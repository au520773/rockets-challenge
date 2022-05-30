package com.backend.challenge.rockets.repository;

import com.backend.challenge.rockets.model.Rocket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RocketsRepository extends JpaRepository<Rocket, String> {

    @Query("SELECT r FROM Rocket r WHERE r.channel = ?1")
    Optional<Rocket> selectRocketByChannel(String channel);
}
