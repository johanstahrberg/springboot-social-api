package se.jensen.johan.springboot.dto;

import java.time.LocalDateTime;

public record PostResponseDto(
        Long id,
        String text,
        LocalDateTime createdAt,
        Long userId
) {
}