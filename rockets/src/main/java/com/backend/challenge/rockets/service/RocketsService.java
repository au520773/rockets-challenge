package com.backend.challenge.rockets.service;

import com.backend.challenge.rockets.exception.InternalServerErrorException;
import com.backend.challenge.rockets.entity.Rocket;
import com.backend.challenge.rockets.repository.RocketsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class RocketsService {

    private final RocketsRepository rocketsRepository;

    public List<Rocket> getAll(String sortBy) {
        if (sortBy == null) {
            return rocketsRepository.findAll();
        }

        try {
            return rocketsRepository.findAll(Sort.by(Sort.Direction.ASC, sortBy));
        } catch (PropertyReferenceException e) {
            return rocketsRepository.findAll();
        }
    }

    public Rocket getByChannel(String channel) {
        return rocketsRepository.selectRocketByChannel(channel).orElseThrow(
                () -> new InternalServerErrorException("Rocket with channel " + channel + " not found")
        );
    }

    public Rocket create(Rocket rocket) {
        Optional<Rocket> optionalRocket = rocketsRepository.selectRocketByChannel(rocket.getChannel());

        if (optionalRocket.isPresent()) {
            throw new InternalServerErrorException("Rocket with channel " + rocket.getChannel() + " already exists");
        }

        return rocketsRepository.save(rocket);
    }

    @Transactional
    public Rocket update(String channel, Consumer<Rocket> rocketConsumer) {
        Rocket rocketToUpdate = rocketsRepository.selectRocketByChannel(channel).orElseThrow(
                () -> new InternalServerErrorException("Rocket with channel " + channel + " not found")
        );

        rocketConsumer.accept(rocketToUpdate);

        return rocketToUpdate;
    }
}
