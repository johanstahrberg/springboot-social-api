package se.jensen.johan.springboot.dto;

import java.util.List;

/**
 * DTO used when returning a user with posts.
 *
 * @param user  user data
 * @param posts list of posts for the user
 */
public record UserWithPostsResponseDto(
        UserResponseDto user,
        List<PostResponseDto> posts
) {
}