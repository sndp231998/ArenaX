package com.arinax.playloads;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PostDto {

	private Integer postId;
	
	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date addedDate;	
	
	private GameDto game;

	private UserDto user;
	
	private GameModeDto gameMode;
	
	//private Set<CommentDto> comments=new HashSet<>();

	
	
	
	
	
	
}
