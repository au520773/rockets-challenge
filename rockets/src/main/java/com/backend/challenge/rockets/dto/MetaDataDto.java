package com.backend.challenge.rockets.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MetaDataDto {
    @NotNull private String channel;
    private Integer messageNumber;
    private String messageTime;
    private String messageType;
}
