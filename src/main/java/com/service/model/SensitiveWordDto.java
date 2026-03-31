package com.service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for SensitiveWord")
public class SensitiveWordDto {
    @Schema(description = "Unique ID of the sensitive word", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Word cannot be blank")
    @Schema(description = "The word to be filtered", example = "SELECT")
    private String word;

    @Schema(description = "The timestamp when the sensitive word was created", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "The timestamp when the sensitive word was last updated", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;
}
