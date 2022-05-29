package com.backend.challenge.rockets.controller;

import com.backend.challenge.rockets.messages.MessageRequest;
import com.backend.challenge.rockets.service.RocketsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/messages")
public class MessagesController {

    private final RocketsService rocketsService;

    public MessagesController(RocketsService rocketsService) {
        this.rocketsService = rocketsService;
    }

    @PostMapping
    public void publish(@Valid @RequestBody MessageRequest messageRequest) {
        rocketsService.addRocket(messageRequest);
    }

    /*@GetMapping(path = "{channel}")
    public ResponseEntity<ProgramDto> get(@PathVariable("channel") String channel) {

    }*/
}
