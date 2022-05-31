package com.backend.challenge.rockets.controller;

import com.backend.challenge.rockets.dto.MessageDto;
import com.backend.challenge.rockets.service.MessageProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/messages")
public class MessagesController {

    private final MessageProducerService rocketsService;

    @PostMapping
    public void publish(@Valid @RequestBody MessageDto messageDto) {
        rocketsService.addMessage(messageDto);
    }
}
