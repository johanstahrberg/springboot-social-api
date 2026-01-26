package se.jensen.johan.springboot.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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


    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAll() {
        return ResponseEntity.ok(postService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getOne(@PathVariable Long id) {
        PostResponseDto post = postService.findById(id); // kastar exception om saknas
        return ResponseEntity.ok(post);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody PostRequestDto request) {

        PostResponseDto updated = postService.update(id, request); // kastar exception om saknas
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id); // void + kastar exception om saknas
        return ResponseEntity.noContent().build();
    }
}