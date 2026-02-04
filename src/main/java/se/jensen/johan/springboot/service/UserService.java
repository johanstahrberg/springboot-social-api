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
import se.jensen.johan.springboot.mapper.UserMapper;
import se.jensen.johan.springboot.model.User;
import se.jensen.johan.springboot.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Service for users.
 * Used to create, read, update and delete users.
 */
@Service
@Transactional
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates the service.
     *
     * @param userRepository  repository for users
     * @param userMapper      mapper used to convert between user and DTO
     * @param passwordEncoder encoder used for passwords
     */
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates a new user.
     *
     * @param userDto data for the user
     * @return created user
     */
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
        return UserMapper.toDto(savedUser);
    }

    /**
     * Returns all users.
     *
     * @return list of users
     */
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

    /**
     * Returns one user by id.
     *
     * @param id id of the user
     * @return the user
     */
    public UserResponseDto getById(Long id) {
        Optional<User> opt = userRepository.findById(id);

        if (!opt.isPresent()) {
            log.warn("User with id {} not found", id);
            throw new NoSuchElementException("User not found with id: " + id);
        }

        return UserMapper.toDto(opt.get());
    }

    /**
     * Updates an existing user.
     *
     * @param id  id of the user
     * @param dto new data for the user
     * @return updated user
     */
    public UserResponseDto update(Long id, UserRequestDto dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User with id {} not found", id);
                    return new NoSuchElementException("Ingen user i databasen med id: " + id);
                });

        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setRole(dto.role());
        user.setDisplayName(dto.displayName());
        user.setProfileImagePath(dto.profileImagePath());
        user.setBio(dto.bio());

        User saved = userRepository.save(user);
        return UserMapper.toDto(saved);
    }

    /**
     * Deletes a user by id.
     *
     * @param id id of the user
     */
    public void delete(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User with id {} not found", id);
                    return new NoSuchElementException("Ingen user i databasen med id: " + id);
                });

        userRepository.deleteById(user.getId());
    }

    /**
     * Returns a user and the user's posts.
     *
     * @param id id of the user
     * @return user with posts
     */
    public UserWithPostsResponseDto getUserWithPosts(Long id) {

        User user = userRepository.findUserWithPosts(id)
                .orElseThrow(() -> {
                    log.warn("User with id {} not found", id);
                    return new NoSuchElementException("User not found with id " + id);
                });

        List<PostResponseDto> posts = user.getPosts().stream()
                .map(p -> new PostResponseDto(
                        p.getId(),
                        p.getText(),
                        p.getCreatedAt(),
                        p.getUser().getId()
                ))
                .toList();

        UserResponseDto userDto = UserMapper.toDto(user);

        return new UserWithPostsResponseDto(userDto, posts);
    }

    /**
     * Finds a user by username.
     *
     * @param username username to search for
     * @return the user
     */
    public UserResponseDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User not found. username={}", username);
                    return new NoSuchElementException("Ingen user i databasen med username: " + username);
                });
        return UserMapper.toDto(user);
    }
}