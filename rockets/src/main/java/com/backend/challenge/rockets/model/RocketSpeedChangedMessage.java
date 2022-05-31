package com.backend.challenge.rockets.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data()
@EqualsAndHashCode(callSuper = true)
public class RocketSpeedChangedMessage extends RocketMessage {
    private Integer by;
}
