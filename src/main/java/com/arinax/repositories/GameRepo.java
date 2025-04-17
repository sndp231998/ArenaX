package com.arinax.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arinax.entities.Game;



public interface GameRepo extends JpaRepository<Game, Integer> {

}
