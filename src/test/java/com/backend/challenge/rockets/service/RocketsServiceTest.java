package com.backend.challenge.rockets.service;

import com.backend.challenge.rockets.entity.Rocket;
import com.backend.challenge.rockets.exception.InternalServerErrorException;
import com.backend.challenge.rockets.repository.RocketsRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class RocketsServiceTest {

    @Mock
    private RocketsRepository rocketsRepository;

    @InjectMocks
    private RocketsService rocketsService;

    private final Rocket rocket = Rocket.builder()
            .channel("0e9e9df5-38fd-52e2-916f-c858b9eb4ea5")
            .status("Alive")
            .type("Atlas-H")
            .speed(500)
            .mission("APOLLO_SOYUZ")
            .build();

    @Test
    void getAll_should_return_unsorted_list_when_no_sorting_applied() {
        // Arrange
        when(rocketsRepository.findAll()).thenReturn(Collections.singletonList(rocket));

        // Act
        List<Rocket> rockets = rocketsService.getAll(null);

        // Assert
        verify(rocketsRepository).findAll();
        assertThat(rockets).hasSize(1);
    }

    @Test
    void getAll_should_return_sorted_list_when_sorting_applied() {
        // Arrange
        when(rocketsRepository.findAll(any(Sort.class))).thenReturn(Collections.singletonList(rocket));

        // Act
        List<Rocket> rockets = rocketsService.getAll("mission");

        // Assert
        verify(rocketsRepository).findAll(Sort.by(Sort.Direction.ASC, "mission"));
        assertThat(rockets).hasSize(1);
    }

    @Test
    void getAll_should_return_unsorted_list_when_failing_to_sort() {
        // Arrange
        when(rocketsRepository.findAll(any(Sort.class))).thenThrow(PropertyReferenceException.class);
        when(rocketsRepository.findAll()).thenReturn(Collections.singletonList(rocket));

        // Act
        List<Rocket> rockets = rocketsService.getAll("unknown property");

        // Assert
        verify(rocketsRepository).findAll();
        assertThat(rockets).hasSize(1);
    }

    @Test
    void getByChannel_should_find_specific_rocket() {
        // Arrange
        when(rocketsRepository.selectRocketByChannel("channel")).thenReturn(Optional.of(rocket));

        // Act
        Rocket result = rocketsService.getByChannel("channel");

        // Assert
        assertThat(result).isEqualTo(rocket);
    }

    @Test
    void getByChannel_should_throw_on_non_existing_rocket() {
        // Arrange
        when(rocketsRepository.selectRocketByChannel("channel")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> rocketsService.getByChannel("channel"))
                .isInstanceOf(InternalServerErrorException.class);
    }

    @Test
    void create_should_throw_on_existing_rocket() {
        // Arrange
        when(rocketsRepository.selectRocketByChannel(anyString())).thenReturn(Optional.of(rocket));

        // Act & Assert
        assertThatThrownBy(() -> rocketsService.create(rocket))
                .isInstanceOf(InternalServerErrorException.class);
    }

    @Test
    void create_should_save_new_rocket() {
        // Arrange
        when(rocketsRepository.selectRocketByChannel(anyString())).thenReturn(Optional.empty());
        when(rocketsRepository.save(any())).thenReturn(rocket);

        // Act
        Rocket result = rocketsService.create(rocket);

        // Assert
        assertThat(result).isEqualTo(rocket);
    }

    @Test
    void update_existing_rocket() {
        // Arrange
        when(rocketsRepository.selectRocketByChannel(anyString())).thenReturn(Optional.of(rocket));

        // Act
        Rocket result = rocketsService.update("channel", (rocket) -> rocket.setSpeed(5000));

        // Assert
        assertThat(result.getSpeed()).isEqualTo(5000);
    }

    @Test
    void update_should_throw_on_non_existing_exercise() {
        // Arrange
        when(rocketsRepository.selectRocketByChannel(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> rocketsService.update("channel", (rocket) -> rocket.setSpeed(5000)))
                .isInstanceOf(InternalServerErrorException.class);
    }
}
