package com.backend.challenge.rockets.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data()
@EqualsAndHashCode(callSuper = true)
public class RocketLaunchedMessageDto extends RocketMessageDto {
    private String type;
    private Integer launchSpeed;
    private String mission;
}
