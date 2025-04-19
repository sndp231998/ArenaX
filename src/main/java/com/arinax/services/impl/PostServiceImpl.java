package com.arinax.services.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.arinax.config.AppConstants;
import com.arinax.entities.Game;
import com.arinax.entities.GameMode;
import com.arinax.entities.Post;
import com.arinax.entities.Role;
import com.arinax.entities.User;
import com.arinax.exceptions.ApiException;
import com.arinax.exceptions.ResourceNotFoundException;
import com.arinax.playloads.PostDto;
import com.arinax.playloads.PostResponse;
import com.arinax.repositories.GameModeRepo;
import com.arinax.repositories.GameRepo;
import com.arinax.repositories.PostRepo;
import com.arinax.repositories.RoleRepo;
import com.arinax.repositories.UserRepo;
import com.arinax.services.NotificationService;
import com.arinax.services.PostService;



@Service
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
    
    @Autowired
    private RoleRepo roleRepo;
    
    @Autowired
    private NotificationService notificationService;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer gameId,Integer modeId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User ", "User id", userId));

        Game game = this.gameRepo.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Game", "game id ", gameId));
        
        GameMode mode = this.modeRepo.findById(modeId)
                .orElseThrow(() -> new ResourceNotFoundException("GameMode", "mode id ", modeId));
        
        Role adminRole = roleRepo.findById(AppConstants.ADMIN_USER)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "Role Id", AppConstants.ADMIN_USER));

        List<User> allAdmins = userRepo.findAllByRoleName(adminRole.getName());

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        
        post.setAddedDate(LocalDateTime.now());
        post.setUser(user);
        post.setGame(game);
        post.setGameMode(mode);
        
        
        if (user.getRoles().stream().anyMatch(role -> role.getName().equals(adminRole.getName()))) {
            post.setStatus(Post.PostStatus.APPROVED);
           
        } else {
            post.setStatus(Post.PostStatus.PENDING);
          
        }
      Post newPost = this.postRepo.save(post);

        if (user.getRoles().stream().anyMatch(role -> role.getName().equals(adminRole.getName()))) {
            // Admin created post => approved directly
            notificationService.createNotification(user.getId(), "Your post has been created successfully.");
            // Optional: if you want to show to admin as self confirmation
            // notificationService.createNotification(user.getId(), "You created a post.");
        } else {
            // Normal user created post => pending
            notificationService.createNotification(user.getId(), "Your post has been submitted and is under review.");
            
            for (User admin : allAdmins) {
                notificationService.createNotification(admin.getId(), "A new post needs approval.");
            }
        }
        return this.modelMapper.map(newPost, PostDto.class);
    }
    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", postId));
        
       

        if (post.getStatus() != Post.PostStatus.PENDING ) {
            throw new ApiException("Cannot update");
        }
        
        Game game = this.gameRepo.findById(postDto.getGame().getGameId())
        	    .orElseThrow(() -> new ResourceNotFoundException("Game", "game id", postDto.getGame().getGameId()));

        	GameMode mode = this.modeRepo.findById(postDto.getGameMode().getModeId())
        	    .orElseThrow(() -> new ResourceNotFoundException("GameMode", "mode id", postDto.getGameMode().getModeId()));
        	Role adminRole = roleRepo.findById(AppConstants.ADMIN_USER)
                    .orElseThrow(() -> new ResourceNotFoundException("Role", "Role Id", AppConstants.ADMIN_USER));

            List<User> allAdmins = userRepo.findAllByRoleName(adminRole.getName());


        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post.setGame(game);
        post.setGameMode(mode);
        post.setStatus(Post.PostStatus.PENDING);
     // Optional: Notify admin after update
        for (User admin : allAdmins) {
            notificationService.createNotification(admin.getId(), "Post updated. Please review again.");
        }

        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    
    @Override
    public PostDto approvePost(Integer postId) {
    	 Post post = this.postRepo.findById(postId)
                 .orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", postId));

        // Only allow approval if status is PENDING
        if (post.getStatus() != Post.PostStatus.PENDING) {//pending hunai paro natra approved hudaina
            throw new ApiException("Cannot approve.");
        }

        post.setStatus(Post.PostStatus.APPROVED);
       
        notificationService.createNotification(post.getUser().getId(),
                "Your post titled '" + post.getTitle() + "' has been APPROVED.");

        Post approvedPost = this.postRepo.save(post);
        return this.modelMapper.map(approvedPost, PostDto.class);
    }
    
    @Override
    public PostDto rejectPost(Integer postId) {
    	 Post post = this.postRepo.findById(postId)
                 .orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", postId));

        // Only allow approval if status is PENDING
        if (post.getStatus() != Post.PostStatus.PENDING) { //pending hunai paro
            throw new IllegalStateException("Cannot reject");
        }

        post.setStatus(Post.PostStatus.REJECTED);
        notificationService.createNotification(post.getUser().getId(),
                "Your post titled '" + post.getTitle() + "' has been REJECTED.");


        Post approvedPost = this.postRepo.save(post);
        return this.modelMapper.map(approvedPost, PostDto.class);
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
        
        Page<Post> pagePost = this.postRepo.findByStatus(Post.PostStatus.APPROVED, p);

       // Page<Post> pagePost = this.postRepo.findAll(p);

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
    //getUnapprovedPosts(0, 10, "addedDate", "desc");

    @Override
    public PostResponse getPostsByStatus(Post.PostStatus status, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePost = postRepo.findByStatus(status, pageable);

        List<PostDto> postDtos = pagePost.getContent()
            .stream()
            .map(post -> modelMapper.map(post, PostDto.class))
            .collect(Collectors.toList());

        PostResponse response = new PostResponse();
        response.setContent(postDtos);
        response.setPageNumber(pagePost.getNumber());
        response.setPageSize(pagePost.getSize());
        response.setTotalElements(pagePost.getTotalElements());
        response.setTotalPages(pagePost.getTotalPages());
        response.setLastPage(pagePost.isLast());

        return response;
    }

   
}
