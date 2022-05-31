package com.backend.challenge.rockets.controller;

import com.backend.challenge.rockets.entity.Rocket;
import com.backend.challenge.rockets.service.RocketsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/rockets")
public class RocketsController {

    private final RocketsService rocketsService;

    @GetMapping()
    public ResponseEntity<List<Rocket>> getAll() {
        return new ResponseEntity<>(
                rocketsService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "{channel}")
    public ResponseEntity<Rocket> getByChannel(@PathVariable("channel") String channel) {
        return new ResponseEntity<>(
                rocketsService.getByChannel(channel),
                HttpStatus.OK
        );
    }
}
