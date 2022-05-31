package com.backend.challenge.rockets.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data()
@EqualsAndHashCode(callSuper = true)
public class RocketLaunchedMessage extends RocketMessage {
    private String type;
    private Integer launchSpeed;
    private String mission;
}
