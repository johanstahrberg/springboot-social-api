package se.jensen.johan.springboot.dto;

import java.util.List;

public record UserWithPostsResponseDto(
        UserResponseDto user,
        List<PostResponseDto> posts
) {
}