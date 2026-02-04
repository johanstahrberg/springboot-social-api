package se.jensen.johan.springboot.dto;

/**
 * DTO used for login requests.
 * Contains username and password.
 *
 * @param username username for login
 * @param password password for login
 */
public record LoginRequestDto(String username, String password) {
}


