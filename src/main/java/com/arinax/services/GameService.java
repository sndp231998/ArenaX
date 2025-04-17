package com.arinax.services;

import java.util.List;

import com.arinax.playloads.GameDto;



public interface GameService {
	// create
		GameDto createGame(GameDto gameDto);

		// update
		GameDto updateGame(GameDto gameDto, Integer gameId);

		// delete
		void deleteGame(Integer gameId);

		// get
		GameDto getGame(Integer gameId);

		// get All

		List<GameDto> getGames();

	}
