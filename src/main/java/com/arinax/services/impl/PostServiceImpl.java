package com.arinax.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.arinax.entities.Game;
import com.arinax.entities.GameMode;
import com.arinax.entities.Post;
import com.arinax.entities.User;
import com.arinax.exceptions.ResourceNotFoundException;
import com.arinax.playloads.PostDto;
import com.arinax.playloads.PostResponse;
import com.arinax.repositories.GameModeRepo;
import com.arinax.repositories.GameRepo;
import com.arinax.repositories.PostRepo;
import com.arinax.repositories.UserRepo;
import com.arinax.services.PostService;


public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private GameRepo gameRepo;
    
    @Autowired
    private GameModeRepo modeRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer gameId,Integer modeId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User ", "User id", userId));

        Game game = this.gameRepo.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Game", "game id ", gameId));
        
        GameMode mode = this.modeRepo.findById(modeId)
                .orElseThrow(() -> new ResourceNotFoundException("GameMode", "mode id ", modeId));
        

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setGame(game);
        post.setGameMode(mode);
        Post newPost = this.postRepo.save(post);

        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", postId));

        Game game = this.gameRepo.findById(postDto.getGame().getGameId())
        	    .orElseThrow(() -> new ResourceNotFoundException("Game", "game id", postDto.getGame().getGameId()));

        	GameMode mode = this.modeRepo.findById(postDto.getGameMode().getModeId())
        	    .orElseThrow(() -> new ResourceNotFoundException("GameMode", "mode id", postDto.getGameMode().getModeId()));


        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post.setGame(game);
        post.setGameMode(mode);
     
        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {

        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", postId));

        this.postRepo.delete(post);

    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePost = this.postRepo.findAll(p);

        List<Post> allPosts = pagePost.getContent();

        List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());

        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByGame(Integer gameId) {

        Game cat = this.gameRepo.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Game", "game id", gameId));
        List<Post> posts = this.postRepo.findByGame(cat);

        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User ", "userId ", userId));
        List<Post> posts = this.postRepo.findByUser(user);

        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepo.searchByTitle("%" + keyword + "%");
        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

}
