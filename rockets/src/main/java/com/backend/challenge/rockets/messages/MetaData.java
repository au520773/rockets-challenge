package com.backend.challenge.rockets.messages;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MetaData {
    @NotNull private String channel;
    private Integer messageNumber;
    private String messageTime;
    private String messageType;
}
