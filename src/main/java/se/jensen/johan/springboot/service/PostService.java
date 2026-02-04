package se.jensen.johan.springboot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.johan.springboot.dto.PostRequestDto;
import se.jensen.johan.springboot.dto.PostResponseDto;
import se.jensen.johan.springboot.model.Post;
import se.jensen.johan.springboot.model.User;
import se.jensen.johan.springboot.repository.PostRepository;
import se.jensen.johan.springboot.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service for posts.
 * Used to create, read, update and delete posts.
 */
@Service
@Transactional
public class PostService {

    private static final Logger log = LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * Creates the service.
     *
     * @param postRepository repository for posts
     * @param userRepository repository for users
     */
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    /**
     * Converts a Post to a response DTO.
     *
     * @param post post entity
     * @return post response DTO
     */
    private PostResponseDto toResponse(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getText(),
                post.getCreatedAt(),
                post.getUser().getId()
        );
    }

    /**
     * Returns the current user based on authentication.
     *
     * @param auth authentication info
     * @return current user
     */
    private User getCurrentUser(Authentication auth) {
        return userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new NoSuchElementException("User not found: " + auth.getName()));
    }

    /**
     * Checks that the current user owns the post.
     *
     * @param post post to check
     * @param auth authentication info
     */
    private void assertOwner(Post post, Authentication auth) {
        String currentUsername = auth.getName();
        String postOwnerUsername = post.getUser().getUsername();

        if (!postOwnerUsername.equals(currentUsername)) {
            throw new AccessDeniedException("You can only modify your own posts");
        }
    }

    /**
     * Creates a new post for the logged in user.
     *
     * @param auth    authentication info
     * @param postDto data for the post
     * @return created post
     */
    public PostResponseDto createPost(Authentication auth, PostRequestDto postDto) {
        User user = getCurrentUser(auth);

        Post post = new Post();
        post.setText(postDto.text());
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);

        Post savedPost = postRepository.save(post);
        return toResponse(savedPost);
    }

    /**
     * Returns all posts sorted by creation time.
     *
     * @return list of posts
     */
    public List<PostResponseDto> findAll() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Returns all posts for a user sorted by creation time.
     *
     * @param userId id of the user
     * @return list of posts
     */
    public List<PostResponseDto> findWall(Long userId) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Returns one post by id.
     *
     * @param id id of the post
     * @return the post
     */
    public PostResponseDto findById(Long id) {
        return postRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> {
                    log.warn("Post with id {} not found", id);
                    return new NoSuchElementException("Post not found with id " + id);
                });
    }

    /**
     * Updates a post (owner only).
     *
     * @param id   id of the post
     * @param auth authentication info
     * @param dto  new data for the post
     * @return updated post
     */
    public PostResponseDto update(Long id, Authentication auth, PostRequestDto dto) {
        Post existing = postRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Post with id {} not found", id);
                    return new NoSuchElementException("Post not found with id " + id);
                });

        assertOwner(existing, auth);

        existing.setText(dto.text());
        Post updated = postRepository.save(existing);
        return toResponse(updated);
    }

    /**
     * Deletes a post (owner only).
     *
     * @param id   id of the post
     * @param auth authentication info
     */
    public void delete(Long id, Authentication auth) {
        Post existing = postRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Post with id {} not found", id);
                    return new NoSuchElementException("Post not found with id " + id);
                });

        assertOwner(existing, auth);

        postRepository.delete(existing);
    }
}