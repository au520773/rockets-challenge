package com.backend.challenge.rockets.controller;

import com.backend.challenge.rockets.messages.Message;
import com.backend.challenge.rockets.service.MessageConsumerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "/rockets")
public class RocketsController {

    private final MessageConsumerService messageConsumerService;

    public RocketsController(MessageConsumerService messageConsumerService) {
        this.messageConsumerService = messageConsumerService;
    }

    @GetMapping(path = "{channel}")
    public ResponseEntity<List<Message>> get(@PathVariable("channel") String channel) {
        return ResponseEntity.ok(Collections.emptyList());
    }
}
