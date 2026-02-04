package se.jensen.johan.springboot.mapper;

import org.springframework.stereotype.Component;
import se.jensen.johan.springboot.dto.UserRequestDto;
import se.jensen.johan.springboot.dto.UserResponseDto;
import se.jensen.johan.springboot.model.User;

/**
 * Mapper for User objects.
 * Used to convert between User and DTOs.
 */
@Component
public class UserMapper {

    /**
     * Creates a new User from a request DTO.
     *
     * @param dto data for the user
     * @return new user
     */
    public User fromDto(UserRequestDto dto) {
        User user = new User();
        setUserValues(user, dto);
        return user;
    }

    /**
     * Updates an existing User from a request DTO.
     *
     * @param user existing user
     * @param dto  new data for the user
     * @return updated user
     */
    public User fromDto(User user, UserRequestDto dto) {
        setUserValues(user, dto);
        return user;
    }

    /**
     * Sets values on a User from a request DTO.
     *
     * @param user user to update
     * @param dto  data for the user
     */
    private void setUserValues(User user, UserRequestDto dto) {
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setRole(dto.role());
        user.setDisplayName(dto.displayName());
        user.setBio(dto.bio());
        user.setProfileImagePath(dto.profileImagePath());
    }

    /**
     * Creates a response DTO from a User.
     *
     * @param user user data
     * @return user response DTO
     */
    public static UserResponseDto toDto(User user) {
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
}