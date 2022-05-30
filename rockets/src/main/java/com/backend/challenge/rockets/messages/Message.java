package com.backend.challenge.rockets.messages;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
public class Message {

    private MetaData metadata;

    @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = RocketLaunchedMessage.class),
            @JsonSubTypes.Type(value = RocketSpeedChangedMessage.class),
            @JsonSubTypes.Type(value = RocketExplodedMessage.class),
            @JsonSubTypes.Type(value = RocketMissionChangedMessage.class)
    })
    private RocketMessage message;

}
