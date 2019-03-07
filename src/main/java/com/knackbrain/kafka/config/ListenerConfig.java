package com.knackbrain.kafka.config;

import com.knackbrain.kafka.KafkaApplication;
import com.knackbrain.kafka.model.Message;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.knackbrain.kafka.util.KafkaConstant.*;

@Configuration
public class ListenerConfig {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaApplication.class);

    @KafkaListener(id=messageGroup, topics = topicName)
    public void messageListener(final Message message) {
        LOGGER.info("Message received: \n id: {} \n message: {}", message.getId(), message.getMessage());
    }

    @KafkaListener(id = errorMessageGroup, topics = errorTopicName)
    public void errorMessageListener(final Message message) {
        LOGGER.info("Message with id: {} got error!", message.getId());
    }
}
