package com.backend.challenge.rockets.model;

import com.backend.challenge.rockets.messages.Message;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
@ToString
@RequiredArgsConstructor
public enum MessageType {

    ROCKET_LAUNCHED("RocketLaunched"),
    ROCKET_SPEED_INCREASED("RocketSpeedIncreased"),
    ROCKET_SPEED_DECREASED("RocketSpeedDecreased"),
    ROCKET_EXPLODED("RocketExploded"),
    ROCKET_MISSION_CHANGED("RocketMissionChanged");

    private final String messageType;

    public static Optional<MessageType> of(String messageType) {
        return MessageType
                .stream()
                .filter(type -> Objects.equals(type.getMessageType(), messageType))
                .findFirst();
    }

    private static Stream<MessageType> stream() { return Stream.of(values()); }
}
