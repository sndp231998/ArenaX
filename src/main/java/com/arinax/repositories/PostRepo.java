package com.arinax.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.arinax.entities.Game;
import com.arinax.entities.Post;
import com.arinax.entities.User;



public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);
	List<Post> findByGame(Game game);	
	
	@Query("select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("key") String title);
	Page<Post> findByStatus(Post.PostStatus status, Pageable pageable);

	Page<Post> findByStatusNot(Post.PostStatus status, Pageable pageable);



}
