package com.arinax.playloads;

import java.time.LocalDateTime;

import com.arinax.entities.Game;
import com.arinax.entities.User;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class RoomDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer roomId;
	

	@Column(length = 1000000000)
	private String content;

	private LocalDateTime addedDate;

	private String startTime;
	
	private double entryFee=0.0;
	
	private double wining;
	private String gameType; // e.g. "1v1", "2v2", ..., "8v8"

	
	private int inventory=0;

	private Game game;
	 
	@ManyToOne
	private User user;
	
}
