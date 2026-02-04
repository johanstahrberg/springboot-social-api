package se.jensen.johan.springboot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import se.jensen.johan.springboot.dto.PostRequestDto;
import se.jensen.johan.springboot.dto.PostResponseDto;
import se.jensen.johan.springboot.model.Post;
import se.jensen.johan.springboot.model.User;
import se.jensen.johan.springboot.repository.PostRepository;
import se.jensen.johan.springboot.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    Authentication auth;

    @InjectMocks
    PostService postService;

    @Test
    void findById_shouldThrow_whenPostNotFound() {
        when(postRepository.findById(123L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> postService.findById(123L));
    }

    @Test
    void createPost_shouldReturnResponseDto_whenHappyPath() {
        PostRequestDto dto = new PostRequestDto("hello");

        User user = new User();
        user.setId(7L);
        user.setUsername("johan");

        when(auth.getName()).thenReturn("johan");
        when(userRepository.findByUsername("johan")).thenReturn(Optional.of(user));

        Post savedPost = new Post();
        savedPost.setId(99L);
        savedPost.setText("hello");
        savedPost.setCreatedAt(LocalDateTime.now());
        savedPost.setUser(user);

        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        PostResponseDto result = postService.createPost(auth, dto);

        assertNotNull(result);
        assertEquals(99L, result.id());
        assertEquals("hello", result.text());
        assertEquals(7L, result.userId());
    }
}