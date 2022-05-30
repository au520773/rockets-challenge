package com.backend.challenge.rockets.controller;

import com.backend.challenge.rockets.messages.Message;
import com.backend.challenge.rockets.service.MessageProducerService;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/messages")
public class MessagesController {

    private final MessageProducerService rocketsService;

    public MessagesController(MessageProducerService rocketsService) {
        this.rocketsService = rocketsService;
    }

    @PostMapping
    public void publish(@Valid @RequestBody Message message) {
        rocketsService.addRocket(message);
    }
}
