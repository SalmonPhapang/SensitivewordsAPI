package com.service.controller;

import com.service.model.WordCheckRequest;
import com.service.model.WordCheckResponse;
import com.service.service.WordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/words")
@RequiredArgsConstructor
@Tag(name = "${api.filtering.tag.name}", description = "${api.filtering.tag.desc}")
public class WordController {
    private final WordService wordService;

    @Value("${api.health.message}")
    private String healthMessage;

    @PostMapping("/check")
    @Operation(summary = "${api.words.check.summary}", description = "${api.words.check.desc}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.words.check.200}",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = WordCheckResponse.class))}),
            @ApiResponse(responseCode = "400", description = "${api.words.check.400}",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "${api.words.check.401}",
                    content = @Content)
    })
    public ResponseEntity<WordCheckResponse> checkWords(@Valid @RequestBody WordCheckRequest request) {
        return ResponseEntity.ok(wordService.checkAndFilter(request.getText()));
    }

    @GetMapping("/health")
    @Operation(summary = "${api.words.health.summary}", description = "${api.words.health.desc}")
    @ApiResponse(responseCode = "200", description = "${api.words.health.200}")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok(healthMessage);
    }
}
