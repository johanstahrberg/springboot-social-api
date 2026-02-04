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
import se.jensen.johan.springboot.service.UserService;

import java.util.List;

/**
 * Controller for users.
 * Used to create, get, update and delete users.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * Creates the controller.
     *
     * @param userService service used for users
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Returns all users (admin only).
     *
     * @return list of users
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> allUsers = userService.getAllUsers();
        return ResponseEntity.ok().body(allUsers);
    }

    /**
     * Returns one user by id (admin only).
     *
     * @param id id of the user
     * @return the user
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.getById(id);
        return ResponseEntity.ok().body(userResponseDto);
    }

    /**
     * Creates a new user.
     *
     * @param request data for the new user
     * @return created user
     */
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto request) {
        UserResponseDto created = userService.addUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Updates an existing user.
     *
     * @param id      id of the user
     * @param request new data for the user
     * @return updated user
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDto request
    ) {
        UserResponseDto updated = userService.update(id, request);
        return ResponseEntity.ok().body(updated);
    }

    /**
     * Deletes a user.
     *
     * @param id id of the user
     * @return empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Returns a user and the user's posts.
     *
     * @param id id of the user
     * @return user with posts
     */
    @GetMapping("/{id}/with-posts")
    public ResponseEntity<UserWithPostsResponseDto> getUserWithPosts(@PathVariable Long id) {
        UserWithPostsResponseDto response = userService.getUserWithPosts(id);
        return ResponseEntity.ok().body(response);
    }

    /**
     * Returns the current user.
     *
     * @param authentication login info for the user
     * @return the current user
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMe(Authentication authentication) {
        String username = authentication.getName();
        UserResponseDto me = userService.findByUsername(username);
        return ResponseEntity.ok().body(me);
    }
}