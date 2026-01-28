package se.jensen.johan.springboot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.johan.springboot.dto.PostResponseDto;
import se.jensen.johan.springboot.dto.UserRequestDto;
import se.jensen.johan.springboot.dto.UserResponseDto;
import se.jensen.johan.springboot.dto.UserWithPostsResponseDto;
import se.jensen.johan.springboot.mapper.userMapper;
import se.jensen.johan.springboot.model.User;
import se.jensen.johan.springboot.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final userMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, userMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    public UserResponseDto addUser(UserRequestDto userDto) {
        User user = userMapper.fromDto(userDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        boolean exists = userRepository.existsByUsernameOrEmail(
                user.getUsername(),
                user.getEmail()
        );

        if (exists) {
            throw new IllegalArgumentException(
                    "User med detta username eller email finns redan i databasen"
            );
        }

        User savedUser = userRepository.save(user);
        return se.jensen.johan.springboot.mapper.userMapper.toDto(savedUser);
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> new UserResponseDto(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole(),
                        user.getDisplayName(),
                        user.getBio(),
                        user.getProfileImagePath()
                ))
                .toList();
    }

    // Loggning
    public UserResponseDto getById(Long id) {
        Optional<User> opt = userRepository.findById(id);

        if (!opt.isPresent()) {
            log.warn("User with id {} not found", id);
            throw new NoSuchElementException("User not found with id: " + id);
        }

        return se.jensen.johan.springboot.mapper.userMapper.toDto(opt.get());
    }

    /* =========================
       UPDATE
       ========================= */
    public UserResponseDto update(Long id, UserRequestDto dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Ingen user i databasen med id: " + id
                ));

        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setRole(dto.role());
        user.setDisplayName(dto.displayName());
        user.setProfileImagePath(dto.profileImagePath());
        user.setBio(dto.bio());

        User saved = userRepository.save(user);
        return se.jensen.johan.springboot.mapper.userMapper.toDto(saved);
    }

    /* =========================
       DELETE
       ========================= */
    public void delete(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Ingen user i databasen med id: " + id
                ));

        userRepository.deleteById(user.getId());
    }

    /* =========================
       USER WITH POSTS (JOIN FETCH)
       ========================= */
    public UserWithPostsResponseDto getUserWithPosts(Long id) {

        User user = userRepository.findUserWithPosts(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "User not found with id " + id
                ));

        List<PostResponseDto> posts = user.getPosts().stream()
                .map(p -> new PostResponseDto(
                        p.getId(),
                        p.getText(),
                        p.getCreatedAt(),
                        p.getUser().getId()
                ))
                .toList();

        UserResponseDto userDto = se.jensen.johan.springboot.mapper.userMapper.toDto(user);

        return new UserWithPostsResponseDto(userDto, posts);
    }

    /* =========================
   READ – findByUsername
   ========================= */
    public UserResponseDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Ingen user i databasen med username: " + username));
        return se.jensen.johan.springboot.mapper.userMapper.toDto(user); // eller din toResponse(user)
    }


}