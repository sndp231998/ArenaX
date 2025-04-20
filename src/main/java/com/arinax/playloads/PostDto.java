package com.arinax.playloads;

import java.time.LocalDateTime;
import java.util.Date;

import com.arinax.entities.Post.PostStatus;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PostDto {

	private Integer postId;
	
	private String title;
	
	private String content;
	
	private LocalDateTime addedDate;

	private LocalDateTime startTime;
	
	private GameDto game;

	private UserDto user;

	private GameModeDto gameMode;
	
	 private PostStatus status;
	 
	 
	
	//private Set<CommentDto> comments=new HashSet<>();

	
	
	
	
	
	
}
