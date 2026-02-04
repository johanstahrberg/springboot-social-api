package se.jensen.johan.springboot.dto;

import java.time.LocalDateTime;

/**
 * DTO used when returning a post.
 *
 * @param id        id of the post
 * @param text      text of the post
 * @param createdAt time when the post was created
 * @param userId    id of the user who created the post
 */
public record PostResponseDto(
        Long id,
        String text,
        LocalDateTime createdAt,
        Long userId
) {
}