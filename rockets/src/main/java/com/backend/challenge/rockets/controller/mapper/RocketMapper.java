package com.backend.challenge.rockets.controller.mapper;

import com.backend.challenge.rockets.dto.RocketDto;
import com.backend.challenge.rockets.entity.Rocket;
import org.springframework.stereotype.Component;

@Component
public class RocketMapper {
    public RocketDto toDto(Rocket entity) {
        return RocketDto.builder()
                .channel(entity.getChannel())
                .status(entity.getStatus())
                .type(entity.getType())
                .speed(entity.getSpeed())
                .mission(entity.getMission())
                .build();
    }
}
