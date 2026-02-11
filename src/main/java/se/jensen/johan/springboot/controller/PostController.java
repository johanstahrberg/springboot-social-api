package se.jensen.johan.springboot.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import se.jensen.johan.springboot.dto.PostRequestDto;
import se.jensen.johan.springboot.dto.PostResponseDto;
import se.jensen.johan.springboot.service.PostService;

import java.util.List;

/**
 * Controller for posts.
 * Used to handle requests related to posts.
 */
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    /**
     * Creates the controller.
     *
     * @param postService service used for posts
     */
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Creates a new post.
     *
     * @param auth    logged in user
     * @param request data for the post
     * @return created post
     */
    @PostMapping
    public ResponseEntity<PostResponseDto> create(
            Authentication auth,
            @Valid @RequestBody PostRequestDto request) {

        PostResponseDto created = postService.createPost(auth, request);
        return ResponseEntity.ok(created);
    }

    /**
     * Returns all posts.
     *
     * @return list of posts
     */
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    /**
     * Returns one post by id.
     *
     * @param id id of the post
     * @return the post
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    /**
     * Updates an existing post.
     *
     * @param id      id of the post
     * @param auth    logged in user
     * @param request new data for the post
     * @return updated post
     */
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> update(
            @PathVariable Long id,
            Authentication auth,
            @Valid @RequestBody PostRequestDto request) {

        PostResponseDto updated = postService.update(id, auth, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            Authentication auth) {

        postService.delete(id, auth);
        return ResponseEntity.noContent().build();
    }

}