package com.backend.challenge.rockets.service;

import com.backend.challenge.rockets.config.ConversionUtil;
import com.backend.challenge.rockets.messages.Message;
import com.backend.challenge.rockets.messages.RocketLaunchedMessage;
import com.backend.challenge.rockets.model.MessageType;
import com.backend.challenge.rockets.model.Rocket;
import com.backend.challenge.rockets.repository.MessageConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class MessageConsumerService {
    private final MessageConsumer messageConsumer;
    private final RocketsService rocketsService;

    public MessageConsumerService(MessageConsumer messageConsumer, RocketsService rocketsService) {
        this.messageConsumer = messageConsumer;
        this.rocketsService = rocketsService;
    }

    @KafkaListener(
            topics = "rockets",
            groupId = "groupId"
    )
    void listener(String data) {
        Message message = ConversionUtil.jsonToObject(data, Message.class, () -> "failed to deserialize message data");

        MessageType.of(message.getMetadata().getMessageType()).ifPresentOrElse(messageType -> {
            switch (messageType) {
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
                default -> {

                }
            }
        }, () -> {
            throw new RuntimeException("Could not consume data due to unknown messageType");
        });
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

        rocketsService.create(rocket);
    }

    private void handleRocketSpeedIncrease(Message message) {

    }

    private void handleRocketSpeedDecrease(Message message) {

    }

    private void handleRocketMissionChange(Message message) {

    }

    private void handleRocketExplosion(Message message) {

    }


}
