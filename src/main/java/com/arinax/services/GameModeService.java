package com.arinax.services;

import java.util.List;

import com.arinax.playloads.GameDto;
import com.arinax.playloads.GameModeDto;

public interface GameModeService {

	// create
			GameModeDto createGameMode(GameModeDto gameModeDto);

			// update
			GameModeDto updateGameMode(GameModeDto gameModeDto, Integer modeId);

			// delete
			void deleteGameMode(Integer modeId);

			// get
			GameModeDto getGameMode(Integer modeId);

			// get All

			List<GameModeDto> getGameModes();

}
