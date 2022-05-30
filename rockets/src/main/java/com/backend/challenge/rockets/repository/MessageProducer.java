package com.backend.challenge.rockets.repository;

import com.backend.challenge.rockets.messages.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    private final KafkaTemplate<String, Message> template;

    public MessageProducer(KafkaTemplate<String, Message> template) {
        this.template = template;
    }

    public void addMessage(Message message) {
        this.template.send("rockets", message);
    }
}
