package com.arinax.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arinax.entities.GameMode;

public interface GameModeRepo extends JpaRepository<GameMode, Integer> {

}
