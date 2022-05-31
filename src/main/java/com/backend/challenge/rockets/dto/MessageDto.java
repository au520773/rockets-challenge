package com.backend.challenge.rockets.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
public class MessageDto {

    private MetaDataDto metadata;

    @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = RocketLaunchedMessageDto.class),
            @JsonSubTypes.Type(value = RocketSpeedChangedMessageDto.class),
            @JsonSubTypes.Type(value = RocketExplodedMessageDto.class),
            @JsonSubTypes.Type(value = RocketMissionChangedMessageDto.class)
    })
    private RocketMessageDto message;

}
