package se.jensen.johan.springboot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.johan.springboot.dto.PostResponseDto;
import se.jensen.johan.springboot.dto.UserRequestDto;
import se.jensen.johan.springboot.dto.UserResponseDto;
import se.jensen.johan.springboot.dto.UserWithPostsResponseDto;
import se.jensen.johan.springboot.model.User;
import se.jensen.johan.springboot.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserServiceAdvanced {

    private final UserRepository userRepository;

    public UserServiceAdvanced(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private UserResponseDto toResponse(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getDisplayName(),
                user.getBio(),
                user.getProfileImagePath()
        );
    }


    public UserResponseDto create(UserRequestDto request) {
        User user = new User(
                null,
                request.username(),
                request.email(),
                request.password(),
                request.role(),
                request.displayName(),
                request.bio(),
                request.profileImagePath()
        );

        User saved = userRepository.save(user);
        return toResponse(saved);
    }


    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }


    public UserResponseDto findById(Long id) {
        return userRepository.findById(id)
                .map(this::toResponse)
                .orElse(null);
    }


    public UserResponseDto update(Long id, UserRequestDto request) {

        return userRepository.findById(id)
                .map(existing -> {

                    existing.setUsername(request.username());
                    existing.setEmail(request.email());
                    existing.setPassword(request.password());
                    existing.setRole(request.role());
                    existing.setDisplayName(request.displayName());
                    existing.setBio(request.bio());
                    existing.setProfileImagePath(request.profileImagePath());

                    User updated = userRepository.save(existing);
                    return toResponse(updated);

                })
                .orElse(null);
    }


    public boolean delete(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    public UserWithPostsResponseDto getUserWithPosts(Long id) {

        User user = userRepository.findUserWithPosts(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + id));

        List<PostResponseDto> posts = user.getPosts().stream()
                .map(p -> new PostResponseDto(
                        p.getId(),
                        p.getText(),
                        p.getCreatedAt(),
                        p.getUser().getId()
                ))
                .toList();

        UserResponseDto dto = new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getDisplayName(),
                user.getBio(),
                user.getProfileImagePath()
        );

        return new UserWithPostsResponseDto(dto, posts);
    }


}