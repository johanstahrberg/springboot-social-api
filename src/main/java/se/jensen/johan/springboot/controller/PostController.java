package se.jensen.johan.springboot.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import se.jensen.johan.springboot.dto.PostRequestDto;
import se.jensen.johan.springboot.dto.PostResponseDto;
import se.jensen.johan.springboot.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping
    public ResponseEntity<PostResponseDto> create(
            Authentication auth,
            @Valid @RequestBody PostRequestDto request) {

        PostResponseDto created = postService.createPost(auth, request);
        return ResponseEntity.ok(created);
    }


    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAll() {
        return ResponseEntity.ok(postService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }


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