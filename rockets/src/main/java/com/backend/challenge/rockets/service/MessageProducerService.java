package com.backend.challenge.rockets.service;

import com.backend.challenge.rockets.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProducerService {

    private final KafkaTemplate<String, MessageDto> template;

    public void addMessage(MessageDto messageDto) {
        this.template.send("rockets", messageDto);
    }
}
