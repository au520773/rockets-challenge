package com.backend.challenge.rockets.repository;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RocketsProducer {
    private final KafkaTemplate<String, Object> template;

    public RocketsProducer(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    public <T> void addRocket(String key, T t) {
        this.template.send(key, t);
    }
}
