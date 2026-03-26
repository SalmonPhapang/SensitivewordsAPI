package com.service.controller;

import com.service.model.SensitiveWord;
import com.service.service.SensitiveWordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sensitive-words")
@RequiredArgsConstructor
@Tag(name = "CRUD API", description = "Endpoints for managing sensitive words in the database (Internal Consumption)")
public class SensitiveWordController {
    private final SensitiveWordService sensitiveWordService;

    @PostMapping
    @Operation(summary = "Create a new sensitive word", description = "Adds a new sensitive word to the database.")
    public ResponseEntity<SensitiveWord> create(@RequestBody SensitiveWord sensitiveWord) {
        return ResponseEntity.ok(sensitiveWordService.create(sensitiveWord));
    }

    @GetMapping
    @Operation(summary = "Get all sensitive words", description = "Returns a list of all sensitive words currently in the database.")
    public ResponseEntity<List<SensitiveWord>> getAll() {
        return ResponseEntity.ok(sensitiveWordService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get sensitive word by ID", description = "Retrieves a single sensitive word by its unique ID.")
    public ResponseEntity<SensitiveWord> getById(@PathVariable Long id) {
        SensitiveWord sensitiveWord = sensitiveWordService.getById(id);
        return sensitiveWord != null ? ResponseEntity.ok(sensitiveWord) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update sensitive word", description = "Updates an existing sensitive word by its ID.")
    public ResponseEntity<SensitiveWord> update(@PathVariable Long id, @RequestBody SensitiveWord sensitiveWord) {
        SensitiveWord updatedWord = sensitiveWordService.update(id, sensitiveWord);
        return updatedWord != null ? ResponseEntity.ok(updatedWord) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete sensitive word", description = "Removes a sensitive word from the database by its ID.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sensitiveWordService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
