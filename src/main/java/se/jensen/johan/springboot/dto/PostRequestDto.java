package se.jensen.johan.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO used when creating or updating a post.
 * Contains the post text.
 *
 * @param text text of the post
 */
public record PostRequestDto(

        @NotBlank(message = "Text får inte vara tom.")
        @Size(max = 500, message = "Text får max vara 500 tecken.")
        String text

) {
}