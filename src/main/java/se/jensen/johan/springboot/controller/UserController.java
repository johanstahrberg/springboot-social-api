package se.jensen.johan.springboot.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import se.jensen.johan.springboot.dto.UserRequestDto;
import se.jensen.johan.springboot.dto.UserResponseDto;
import se.jensen.johan.springboot.dto.UserWithPostsResponseDto;
import se.jensen.johan.springboot.service.PostService;
import se.jensen.johan.springboot.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PostService postService;

    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    // ✅ ADMIN ONLY
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> allUsers = userService.getAllUsers();
        return ResponseEntity.ok().body(allUsers);
    }

    // ✅ ADMIN ONLY
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.getById(id);
        return ResponseEntity.ok().body(userResponseDto);
    }

    // ✅ Inloggad (permitAll i SecurityConfig låter denna gå utan login)
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto request) {
        UserResponseDto created = userService.addUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDto request
    ) {
        UserResponseDto updated = userService.update(id, request);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}/with-posts")
    public ResponseEntity<UserWithPostsResponseDto> getUserWithPosts(@PathVariable Long id) {
        UserWithPostsResponseDto response = userService.getUserWithPosts(id);
        return ResponseEntity.ok().body(response);
    }

    // ✅ “Nuvarande användare” – bara inloggad användare
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMe(Authentication authentication) {
        String username = authentication.getName();
        UserResponseDto me = userService.findByUsername(username);
        return ResponseEntity.ok().body(me);
    }


}