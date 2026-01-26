package se.jensen.johan.springboot.service;

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


    public PostResponseDto createPost(Long userId, PostRequestDto postDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + userId));

        Post post = new Post();
        post.setText(postDto.text());
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);

        Post savedPost = postRepository.save(post);
        return toResponse(savedPost);
    }

    public List<PostResponseDto> findAll() {
        return postRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public PostResponseDto findById(Long id) {
        return postRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id " + id));
    }

    public PostResponseDto update(Long id, PostRequestDto dto) {
        Post existing = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id " + id));

        existing.setText(dto.text());


        Post updated = postRepository.save(existing);
        return toResponse(updated);
    }

    public void delete(Long id) {
        Post existing = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id " + id));

        postRepository.delete(existing);
    }
}