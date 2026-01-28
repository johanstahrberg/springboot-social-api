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

@Service
@Transactional
public class PostService {

    private static final Logger log = LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    private PostResponseDto toResponse(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getText(),
                post.getCreatedAt(),
                post.getUser().getId()
        );
    }

    private User getCurrentUser(Authentication auth) {
        // Viktigt: detta kräver att du har UserRepository.findByUsername(...)
        return userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new NoSuchElementException("User not found: " + auth.getName()));
    }

    private void assertOwner(Post post, Authentication auth) {
        String currentUsername = auth.getName();
        String postOwnerUsername = post.getUser().getUsername(); // kräver getUsername() i User

        if (!postOwnerUsername.equals(currentUsername)) {
            throw new AccessDeniedException("You can only modify your own posts");
        }
    }

    // CREATE - skapa post för inloggad användare
    public PostResponseDto createPost(Authentication auth, PostRequestDto postDto) {
        User user = getCurrentUser(auth);

        Post post = new Post();
        post.setText(postDto.text());
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);

        Post savedPost = postRepository.save(post);
        return toResponse(savedPost);
    }

    // Global feed - nyast först
    public List<PostResponseDto> findAll() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::toResponse)
                .toList();
    }

    // User wall - nyast först
    public List<PostResponseDto> findWall(Long userId) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    // Logging på denna
    public PostResponseDto findById(Long id) {
        return postRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> {
                    log.warn("Post with id {} not found", id);
                    return new NoSuchElementException("Post not found with id " + id);
                });
    }

    // UPDATE - endast ägaren
    public PostResponseDto update(Long id, Authentication auth, PostRequestDto dto) {
        Post existing = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id " + id));

        assertOwner(existing, auth);

        existing.setText(dto.text());
        Post updated = postRepository.save(existing);
        return toResponse(updated);
    }

    // DELETE - endast ägaren
    public void delete(Long id, Authentication auth) {
        Post existing = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id " + id));

        assertOwner(existing, auth);

        postRepository.delete(existing);
    }
}