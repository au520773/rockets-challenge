package com.backend.challenge.rockets.service;

import com.backend.challenge.rockets.util.ConversionUtil;
import com.backend.challenge.rockets.exception.InternalServerErrorException;
import com.backend.challenge.rockets.model.Message;
import com.backend.challenge.rockets.model.RocketExplodedMessage;
import com.backend.challenge.rockets.model.RocketLaunchedMessage;
import com.backend.challenge.rockets.model.RocketMissionChangedMessage;
import com.backend.challenge.rockets.model.RocketSpeedChangedMessage;
import com.backend.challenge.rockets.model.MessageType;
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
        Message message = ConversionUtil.jsonToObject(data, Message.class, () -> "failed to deserialize message data");

        switch (MessageType.of(message.getMetadata().getMessageType())) {
            case ROCKET_LAUNCHED -> {
                handleRocketLaunch(message);
            }
            case ROCKET_SPEED_INCREASED -> {
                handleRocketSpeedIncrease(message);
            }
            case ROCKET_SPEED_DECREASED -> {
                handleRocketSpeedDecrease(message);
            }
            case ROCKET_MISSION_CHANGED -> {
                handleRocketMissionChange(message);
            }
            case ROCKET_EXPLODED -> {
                handleRocketExplosion(message);
            }
            default -> throw new InternalServerErrorException("Invalid messageType");
        }
    }

    private void handleRocketLaunch(Message message) {
        RocketLaunchedMessage rocketLaunchedMessage = (RocketLaunchedMessage) message.getMessage();

        Rocket rocket = Rocket.builder()
                .channel(message.getMetadata().getChannel())
                .status("Alive")
                .type(rocketLaunchedMessage.getType())
                .speed(rocketLaunchedMessage.getLaunchSpeed())
                .mission(rocketLaunchedMessage.getMission())
                .build();

        log.info("Creating rocket {}", rocket.getChannel());

        rocketsService.create(rocket);
    }

    private void handleRocketSpeedIncrease(Message message) {
        RocketSpeedChangedMessage rocketSpeedChangedMessage = (RocketSpeedChangedMessage) message.getMessage();

        log.info("Increasing speed of rocket {}", message.getMetadata().getChannel());

        rocketsService.update(
                message.getMetadata().getChannel(),
                (rocketToUpdate) -> rocketToUpdate.setSpeed(rocketToUpdate.getSpeed() + rocketSpeedChangedMessage.getBy())
        );
    }

    private void handleRocketSpeedDecrease(Message message) {
        RocketSpeedChangedMessage rocketSpeedChangedMessage = (RocketSpeedChangedMessage) message.getMessage();

        log.info("Decreasing speed of rocket {}", message.getMetadata().getChannel());

        rocketsService.update(
                message.getMetadata().getChannel(),
                (rocketToUpdate) -> rocketToUpdate.setSpeed(rocketToUpdate.getSpeed() - rocketSpeedChangedMessage.getBy())
        );
    }

    private void handleRocketMissionChange(Message message) {
        RocketMissionChangedMessage rocketMissionChangedMessage = (RocketMissionChangedMessage) message.getMessage();

        log.info("Changing mission of rocket {}", message.getMetadata().getChannel());

        rocketsService.update(
                message.getMetadata().getChannel(),
                (rocketToUpdate) -> rocketToUpdate.setMission(rocketMissionChangedMessage.getNewMission())
        );
    }

    private void handleRocketExplosion(Message message) {
        RocketExplodedMessage rocketExplodedMessage = (RocketExplodedMessage) message.getMessage();

        log.info("Explosion of rocket {}", message.getMetadata().getChannel());

        rocketsService.update(
                message.getMetadata().getChannel(),
                (rocketToUpdate) -> rocketToUpdate.setStatus(
                        String.format("Rocket exploded at %s due to %s",
                                message.getMetadata().getMessageTime(),
                                rocketExplodedMessage.getReason()
                        )
                )
        );
    }
}
