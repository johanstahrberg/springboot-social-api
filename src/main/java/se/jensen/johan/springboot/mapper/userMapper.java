package se.jensen.johan.springboot.mapper;

import org.springframework.stereotype.Component;
import se.jensen.johan.springboot.dto.UserRequestDto;
import se.jensen.johan.springboot.dto.UserResponseDto;
import se.jensen.johan.springboot.model.User;

@Component
public class userMapper {


    public User fromDto(UserRequestDto dto) {
        User user = new User();
        setUserValues(user, dto);
        return user;
    }

    public User fromDto(User user, UserRequestDto dto) {
        setUserValues(user, dto);
        return user;
    }

    private void setUserValues(User user, UserRequestDto dto) {
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setRole(dto.role());
        user.setDisplayName(dto.displayName());
        user.setBio(dto.bio());
        user.setProfileImagePath(dto.profileImagePath());
    }

    public static UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto(
                user.getId(), user.getUsername(), user.getEmail(),
                user.getRole(), user.getDisplayName(), user.getBio(),
                user.getProfileImagePath());
        return dto;

    }
}