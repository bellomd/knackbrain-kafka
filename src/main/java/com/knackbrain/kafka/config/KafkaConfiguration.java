package com.knackbrain.kafka.config;

import com.knackbrain.kafka.KafkaApplication;
import com.knackbrain.kafka.model.Message;

import static com.knackbrain.kafka.util.KafkaConstant.errorTopicName;
import static com.knackbrain.kafka.util.KafkaConstant.topicName;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
public class KafkaConfiguration {

    @Value("${kafka.message.topic.partitions:1}")
    private int partitionCount;

    @Value("${kafka.message.topic.replicas:1}")
    private int replicaCount;

    @Value("${kafka.message.error.topic.partitions:1}")
    private int errorPartitionCount;

    @Value("${kafka.message.error.topic.replicas:1}")
    private int errorReplicaCount;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            final ConcurrentKafkaListenerContainerFactoryConfigurer containerFactoryConfigurer,
            final ConsumerFactory<Object, Object> kafkaConsumerFactory,
            final KafkaTemplate<Object, Object> kafkaTemplate) {

        final ConcurrentKafkaListenerContainerFactory<Object, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        containerFactoryConfigurer.configure(factory, kafkaConsumerFactory);
        factory.setErrorHandler(new SeekToCurrentErrorHandler(
                new DeadLetterPublishingRecoverer(kafkaTemplate), 3));

        return factory;
    }

    @Bean
    public RecordMessageConverter converter() {
        return new StringJsonMessageConverter();
    }

    @Bean
    public NewTopic messageTopic() {
        return new NewTopic(topicName, partitionCount, (short) replicaCount);
    }

    @Bean
    public NewTopic errorMessageTopic() {
        return new NewTopic(errorTopicName, errorPartitionCount, (short) errorReplicaCount);
    }
}
