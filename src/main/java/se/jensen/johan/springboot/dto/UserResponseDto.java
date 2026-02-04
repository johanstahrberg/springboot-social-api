package se.jensen.johan.springboot.dto;

/**
 * DTO used when returning user data.
 *
 * @param id               id of the user
 * @param username         username of the user
 * @param email            email address of the user
 * @param role             role of the user
 * @param displayName      display name of the user
 * @param bio              bio text of the user
 * @param profileImagePath path to profile image
 */
public record UserResponseDto(
        Long id,
        String username,
        String email,
        String role,
        String displayName,
        String bio,
        String profileImagePath
) {
}