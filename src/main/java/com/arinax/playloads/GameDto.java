package com.arinax.playloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GameDto {

	private Integer gameId;
	@NotBlank
	@Size(min = 4,message = "Min size of game title is 4")
	private String gameTitle;

	@NotBlank
	@Size(min = 10, message = "min size of game desc is 10")
	private String gameDescription;

}