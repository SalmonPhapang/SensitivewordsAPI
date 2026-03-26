package com.service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request object for sensitive word check")
public class WordCheckRequest {
    @NotBlank(message = "Text cannot be empty")
    @Schema(description = "The text to be checked for sensitive words", example = "SELECT * FROM users")
    private String text;
}
