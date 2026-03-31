package com.service.controller;

import com.service.model.SensitiveWordDto;
import com.service.service.SensitiveWordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the sensitive word",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SensitiveWordDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    public ResponseEntity<SensitiveWordDto> create(@RequestBody @NonNull SensitiveWordDto dto) {
        return ResponseEntity.ok(sensitiveWordService.create(dto));
    }

    @GetMapping
    @Operation(summary = "Get all sensitive words (Paginated)", description = "Returns a paginated list of all sensitive words currently in the database.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the paginated list")
    public ResponseEntity<Page<SensitiveWordDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(sensitiveWordService.getAll(pageable));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all sensitive words", description = "Returns a list of all sensitive words currently in the database.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all words",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = SensitiveWordDto.class)))})
    public ResponseEntity<List<SensitiveWordDto>> getAll() {
        return ResponseEntity.ok(sensitiveWordService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get sensitive word by ID", description = "Retrieves a single sensitive word by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the sensitive word",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SensitiveWordDto.class))}),
            @ApiResponse(responseCode = "404", description = "Sensitive word not found",
                    content = @Content)
    })
    public ResponseEntity<SensitiveWordDto> getById(@PathVariable @NonNull Long id) {
        SensitiveWordDto dto = sensitiveWordService.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update sensitive word", description = "Updates an existing sensitive word by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the sensitive word",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SensitiveWordDto.class))}),
            @ApiResponse(responseCode = "404", description = "Sensitive word not found",
                    content = @Content)
    })
    public ResponseEntity<SensitiveWordDto> update(@PathVariable @NonNull Long id, @RequestBody SensitiveWordDto dto) {
        SensitiveWordDto updatedDto = sensitiveWordService.update(id, dto);
        return updatedDto != null ? ResponseEntity.ok(updatedDto) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete sensitive word", description = "Removes a sensitive word from the database by its ID.")
    @ApiResponse(responseCode = "204", description = "Successfully deleted the sensitive word")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Long id) {
        sensitiveWordService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
