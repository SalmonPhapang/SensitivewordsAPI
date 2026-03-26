package com.service.controller;

import com.service.model.WordCheckRequest;
import com.service.model.WordCheckResponse;
import com.service.service.WordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/words")
@RequiredArgsConstructor
@Tag(name = "Filtering API", description = "Endpoints for checking and filtering sensitive words (External Consumption)")
public class WordController {
    private final WordService wordService;

    @PostMapping("/check")
    @Operation(summary = "Check and filter text", description = "Checks the input text for any sensitive words in the database and replaces them with stars.")
    public ResponseEntity<WordCheckResponse> checkWords(@Valid @RequestBody WordCheckRequest request) {
        return ResponseEntity.ok(wordService.checkAndFilter(request.getText()));
    }

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Checks if the API is running.")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Sensitive Words API is running");
    }
}
