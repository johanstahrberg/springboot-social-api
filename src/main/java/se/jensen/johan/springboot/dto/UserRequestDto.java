package se.jensen.johan.springboot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO used when creating or updating a user.
 *
 * @param username         username for the user
 * @param email            email address of the user
 * @param password         password for the user
 * @param role             role of the user
 * @param displayName      display name of the user
 * @param bio              short bio for the user
 * @param profileImagePath path to profile image, can be null
 */
public record UserRequestDto(

        @NotBlank(message = "Username får inte vara tomt.")
        @Size(min = 3, max = 50, message = "Username måste vara mellan 3 och 50 tecken.")
        String username,

        @NotBlank(message = "Email får inte vara tomt.")
        @Email(message = "Email måste vara en giltig adress.")
        String email,

        @NotBlank(message = "Password får inte vara tomt.")
        @Size(min = 6, max = 40, message = "Password måste vara mellan 6 och 40 tecken.")
        String password,

        @NotBlank(message = "Role får inte vara tomt.")
        String role,

        @NotBlank(message = "Display name får inte vara tomt.")
        @Size(max = 30, message = "Display name får max vara 30 tecken.")
        String displayName,

        @NotBlank(message = "Bio får inte vara tomt.")
        @Size(max = 200, message = "Bio får max vara 200 tecken.")
        String bio,

        String profileImagePath
) {
}