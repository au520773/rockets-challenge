package com.backend.challenge.rockets.service;

import com.backend.challenge.rockets.util.ConversionUtil;
import com.backend.challenge.rockets.exception.InternalServerErrorException;
import com.backend.challenge.rockets.dto.MessageDto;
import com.backend.challenge.rockets.dto.RocketExplodedMessageDto;
import com.backend.challenge.rockets.dto.RocketLaunchedMessageDto;
import com.backend.challenge.rockets.dto.RocketMissionChangedMessageDto;
import com.backend.challenge.rockets.dto.RocketSpeedChangedMessageDto;
import com.backend.challenge.rockets.dto.MessageType;
import com.backend.challenge.rockets.entity.Rocket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageConsumerService {
    private final RocketsService rocketsService;

    @KafkaListener(
            topics = "rockets",
            groupId = "groupId"
    )
    void listener(String data) {
        MessageDto messageDto = ConversionUtil.jsonToObject(data, MessageDto.class, () -> "failed to deserialize message data");

        switch (MessageType.of(messageDto.getMetadata().getMessageType())) {
            case ROCKET_LAUNCHED -> {
                handleRocketLaunch(messageDto);
            }
            case ROCKET_SPEED_INCREASED -> {
                handleRocketSpeedIncrease(messageDto);
            }
            case ROCKET_SPEED_DECREASED -> {
                handleRocketSpeedDecrease(messageDto);
            }
            case ROCKET_MISSION_CHANGED -> {
                handleRocketMissionChange(messageDto);
            }
            case ROCKET_EXPLODED -> {
                handleRocketExplosion(messageDto);
            }
            default -> throw new InternalServerErrorException("Invalid messageType");
        }
    }

    private void handleRocketLaunch(MessageDto messageDto) {
        RocketLaunchedMessageDto rocketLaunchedMessageDto = (RocketLaunchedMessageDto) messageDto.getMessage();

        Rocket rocket = Rocket.builder()
                .channel(messageDto.getMetadata().getChannel())
                .status("Alive")
                .type(rocketLaunchedMessageDto.getType())
                .speed(rocketLaunchedMessageDto.getLaunchSpeed())
                .mission(rocketLaunchedMessageDto.getMission())
                .build();

        log.info("Creating rocket {}", rocket.getChannel());

        rocketsService.create(rocket);
    }

    private void handleRocketSpeedIncrease(MessageDto messageDto) {
        RocketSpeedChangedMessageDto rocketSpeedChangedMessage = (RocketSpeedChangedMessageDto) messageDto.getMessage();

        log.info("Increasing speed of rocket {}", messageDto.getMetadata().getChannel());

        rocketsService.update(
                messageDto.getMetadata().getChannel(),
                (rocketToUpdate) -> rocketToUpdate.setSpeed(rocketToUpdate.getSpeed() + rocketSpeedChangedMessage.getBy())
        );
    }

    private void handleRocketSpeedDecrease(MessageDto messageDto) {
        RocketSpeedChangedMessageDto rocketSpeedChangedMessage = (RocketSpeedChangedMessageDto) messageDto.getMessage();

        log.info("Decreasing speed of rocket {}", messageDto.getMetadata().getChannel());

        rocketsService.update(
                messageDto.getMetadata().getChannel(),
                (rocketToUpdate) -> rocketToUpdate.setSpeed(rocketToUpdate.getSpeed() - rocketSpeedChangedMessage.getBy())
        );
    }

    private void handleRocketMissionChange(MessageDto messageDto) {
        RocketMissionChangedMessageDto rocketMissionChangedMessage = (RocketMissionChangedMessageDto) messageDto.getMessage();

        log.info("Changing mission of rocket {}", messageDto.getMetadata().getChannel());

        rocketsService.update(
                messageDto.getMetadata().getChannel(),
                (rocketToUpdate) -> rocketToUpdate.setMission(rocketMissionChangedMessage.getNewMission())
        );
    }

    private void handleRocketExplosion(MessageDto messageDto) {
        RocketExplodedMessageDto rocketExplodedMessageDto = (RocketExplodedMessageDto) messageDto.getMessage();

        log.info("Explosion of rocket {}", messageDto.getMetadata().getChannel());

        rocketsService.update(
                messageDto.getMetadata().getChannel(),
                (rocketToUpdate) -> rocketToUpdate.setStatus(
                        String.format("Rocket exploded at %s due to %s",
                                messageDto.getMetadata().getMessageTime(),
                                rocketExplodedMessageDto.getReason()
                        )
                )
        );
    }
}
