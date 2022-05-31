package com.backend.challenge.rockets.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data()
@EqualsAndHashCode(callSuper = true)
public class RocketMissionChangedMessageDto extends RocketMessageDto {
    private String newMission;
}
