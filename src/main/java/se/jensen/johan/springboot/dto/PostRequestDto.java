package se.jensen.johan.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostRequestDto(

        @NotBlank(message = "Text får inte vara tom.")
        @Size(max = 500, message = "Text får max vara 500 tecken.")
        String text

) {
}