package com.backend.challenge.rockets.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
public class MessageDto {

    private MetaDataDto metadata;

    @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = RocketLaunchedMessageDtoDto.class),
            @JsonSubTypes.Type(value = RocketSpeedChangedMessageDto.class),
            @JsonSubTypes.Type(value = RocketExplodedMessageDtoDto.class),
            @JsonSubTypes.Type(value = RocketMissionChangedMessageDto.class)
    })
    private RocketMessageDto message;

}
