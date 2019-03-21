package com.knackbrain.kafka.rest;

import com.knackbrain.kafka.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

import static com.knackbrain.kafka.util.KafkaConstant.topicName;

@RestController
public class MessageController {

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @Autowired
    public MessageController(final KafkaTemplate<Object, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping(
            path = "/knackbrain/kafka/sms",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void sendSMS(@RequestBody final Message message) {
        this.kafkaTemplate.send(topicName, message);
    }

    @GetMapping(
            path = "/knackbrain/kafka/message/{name}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Message sayHello(@PathVariable final String name) {
        final Message message = new Message();
        message.setId(new Random().nextLong());
        message.setMessage("You are welcome " + name);
        return message;
    }
}
