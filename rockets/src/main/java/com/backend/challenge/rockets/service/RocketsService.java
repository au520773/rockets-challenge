package com.backend.challenge.rockets.service;

import com.backend.challenge.rockets.exception.BadRequestException;
import com.backend.challenge.rockets.exception.NotFoundException;
import com.backend.challenge.rockets.model.Rocket;
import com.backend.challenge.rockets.repository.RocketsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
@Slf4j
public class RocketsService {

    private RocketsRepository rocketsRepository;

    public List<Rocket> getAll() {
        log.info("Fetching all rockets");

        return rocketsRepository.findAll();
    }

    public Rocket getByChannel(String channel) {
        log.info("Fetching rocket with channel {}", channel);

        return rocketsRepository.selectRocketByChannel(channel).orElseThrow(
                () -> new NotFoundException("Rocket with channel " + channel + " not found")
        );
    }

    public Rocket create(Rocket rocket) {
        log.info("Creating rocket with channel {}", rocket.getChannel());

        Optional<Rocket> optionalRocket = rocketsRepository.selectRocketByChannel(rocket.getChannel());

        if (optionalRocket.isPresent()) {
            throw new BadRequestException("Rocket with channel " + rocket.getChannel() + " already exists");
        }

        return rocketsRepository.save(rocket);
    }


    @Transactional
    public Rocket update(String channel, Consumer<Rocket> rocketConsumer) {

        log.info("Increasing speed of rocket with channel {}", channel);

        Rocket rocketToUpdate = rocketsRepository.selectRocketByChannel(channel).orElseThrow(
                () -> new NotFoundException("Rocket with channel " + channel + " not found")
        );

        rocketConsumer.accept(rocketToUpdate);

        return rocketToUpdate;
    }
}
