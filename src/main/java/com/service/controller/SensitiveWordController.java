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
@Tag(name = "${api.crud.tag.name}", description = "${api.crud.tag.desc}")
public class SensitiveWordController {
    private final SensitiveWordService sensitiveWordService;

    @PostMapping
    @Operation(summary = "${api.sensitive.create.summary}", description = "${api.sensitive.create.desc}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.sensitive.create.200}",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SensitiveWordDto.class))}),
            @ApiResponse(responseCode = "400", description = "${api.sensitive.create.400}",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "${api.sensitive.create.401}",
                    content = @Content)
    })
    public ResponseEntity<SensitiveWordDto> create(@RequestBody @NonNull SensitiveWordDto dto) {
        return ResponseEntity.ok(sensitiveWordService.create(dto));
    }

    @GetMapping
    @Operation(summary = "${api.sensitive.get_all_paginated.summary}", description = "${api.sensitive.get_all_paginated.desc}")
    @ApiResponse(responseCode = "200", description = "${api.sensitive.get_all_paginated.200}")
    public ResponseEntity<Page<SensitiveWordDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(sensitiveWordService.getAll(pageable));
    }

    @GetMapping("/all")
    @Operation(summary = "${api.sensitive.get_all.summary}", description = "${api.sensitive.get_all.desc}")
    @ApiResponse(responseCode = "200", description = "${api.sensitive.get_all.200}",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = SensitiveWordDto.class)))})
    public ResponseEntity<List<SensitiveWordDto>> getAll() {
        return ResponseEntity.ok(sensitiveWordService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "${api.sensitive.get_by_id.summary}", description = "${api.sensitive.get_by_id.desc}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.sensitive.get_by_id.200}",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SensitiveWordDto.class))}),
            @ApiResponse(responseCode = "404", description = "${api.sensitive.get_by_id.404}",
                    content = @Content)
    })
    public ResponseEntity<SensitiveWordDto> getById(@PathVariable @NonNull Long id) {
        SensitiveWordDto dto = sensitiveWordService.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "${api.sensitive.update.summary}", description = "${api.sensitive.update.desc}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.sensitive.update.200}",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SensitiveWordDto.class))}),
            @ApiResponse(responseCode = "404", description = "${api.sensitive.update.404}",
                    content = @Content)
    })
    public ResponseEntity<SensitiveWordDto> update(@PathVariable @NonNull Long id, @RequestBody SensitiveWordDto dto) {
        SensitiveWordDto updatedDto = sensitiveWordService.update(id, dto);
        return updatedDto != null ? ResponseEntity.ok(updatedDto) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "${api.sensitive.delete.summary}", description = "${api.sensitive.delete.desc}")
    @ApiResponse(responseCode = "204", description = "${api.sensitive.delete.204}")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Long id) {
        sensitiveWordService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
