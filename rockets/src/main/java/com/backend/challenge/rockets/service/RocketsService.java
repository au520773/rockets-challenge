package com.backend.challenge.rockets.service;

import com.backend.challenge.rockets.messages.MessageRequest;
import com.backend.challenge.rockets.repository.RocketsProducer;
import org.springframework.stereotype.Service;

@Service
public class RocketsService {

    private final RocketsProducer rocketsProducer;

    public RocketsService(RocketsProducer rocketsProducer) {
        this.rocketsProducer = rocketsProducer;
    }

    public void addRocket(MessageRequest messageRequest) {
        this.rocketsProducer.addRocket("rockets", messageRequest);
    }
}
