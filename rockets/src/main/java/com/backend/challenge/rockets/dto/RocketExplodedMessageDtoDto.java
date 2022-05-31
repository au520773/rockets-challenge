package com.backend.challenge.rockets.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data()
@EqualsAndHashCode(callSuper = true)
public class RocketExplodedMessageDtoDto extends RocketMessageDto {
    private String reason;
}
