package com.backend.challenge.rockets.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Data
public class RocketDto {
    String channel;
    String status;
    String type;
    int speed;
    String mission;
}
