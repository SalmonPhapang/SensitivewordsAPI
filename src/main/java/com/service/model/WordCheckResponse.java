package com.service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object for sensitive word check results")
public class WordCheckResponse {
    @Schema(description = "Indicates if any sensitive words were found")
    private boolean hasSensitiveWords;
    
    @Schema(description = "List of sensitive words found in the text")
    private List<String> foundWords;
    
    @Schema(description = "The text with sensitive words replaced by stars")
    private String filteredText;
}
