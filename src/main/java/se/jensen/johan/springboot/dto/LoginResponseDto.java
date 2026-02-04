package se.jensen.johan.springboot.dto;

/**
 * DTO used for login responses.
 * Contains the JWT token.
 *
 * @param token JWT token
 */
public record LoginResponseDto(String token) {
}