package com.backend.challenge.rockets.messages;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data()
@EqualsAndHashCode(callSuper = true)
public class RocketLaunchedMessage extends Message {
    private String type;
    private Integer launchSpeed;
    private String mission;
}
