package com.backend.challenge.rockets.messages;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data()
@EqualsAndHashCode(callSuper = true)
public class RocketExplodedMessage extends Message {
    private String reason;
}
