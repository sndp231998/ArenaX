package com.arinax.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.arinax.entities.Game;

import com.arinax.entities.Room;
import com.arinax.entities.User;

public interface RoomRepo extends JpaRepository<Room, Integer> {

	List<Room> findByUser(User user);
	List<Room> findByGame(Game game);	
	
	@Query("SELECT r FROM Room r WHERE r.entryFee = :key")
	List<Room> searchByentryFee(@Param("key") Double key);

	@Query("SELECT r FROM Room r WHERE r.entryFee = :entryFee AND r.gameType = :gameType")
	List<Room> searchByEntryFeeAndGameType(@Param("entryFee") Double entryFee, @Param("gameType") String gameType);

}
