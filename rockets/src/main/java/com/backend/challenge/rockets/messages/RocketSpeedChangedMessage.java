package com.backend.challenge.rockets.messages;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data()
@EqualsAndHashCode(callSuper = true)
public class RocketSpeedChangedMessage extends Message {
    private Integer by;
}
