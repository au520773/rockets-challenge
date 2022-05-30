package com.backend.challenge.rockets.service;

import com.backend.challenge.rockets.messages.Message;
import com.backend.challenge.rockets.repository.MessageProducer;
import org.springframework.stereotype.Service;

@Service
public class MessageProducerService {

    private final MessageProducer messageProducer;

    public MessageProducerService(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    public void addRocket(Message message) {
        this.messageProducer.addMessage(message);
    }
}
