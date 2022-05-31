package com.backend.challenge.rockets.controller;

import com.backend.challenge.rockets.controller.mapper.RocketMapper;
import com.backend.challenge.rockets.dto.RocketDto;
import com.backend.challenge.rockets.entity.Rocket;
import com.backend.challenge.rockets.service.RocketsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/rockets")
public class RocketsController {

    private final RocketsService rocketsService;
    private final RocketMapper mapper;

    @GetMapping()
    public ResponseEntity<List<RocketDto>> getAll(@RequestParam(required = false) String sortBy) {
        List<Rocket> rockets = rocketsService.getAll(sortBy);
        return new ResponseEntity<>(
                rockets
                        .stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "{channel}")
    public ResponseEntity<RocketDto> getByChannel(@PathVariable("channel") String channel) {
        Rocket rocket = rocketsService.getByChannel(channel);
        return new ResponseEntity<>(
                mapper.toDto(rocket),
                HttpStatus.OK
        );
    }
}
