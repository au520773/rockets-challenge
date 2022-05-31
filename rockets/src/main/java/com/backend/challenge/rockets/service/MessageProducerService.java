package com.backend.challenge.rockets.service;

import com.backend.challenge.rockets.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProducerService {

    private final KafkaTemplate<String, Message> template;

    public void addMessage(Message message) {
        this.template.send("rockets", message);
    }
}
