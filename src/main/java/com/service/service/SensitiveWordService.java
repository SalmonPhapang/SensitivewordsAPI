package com.service.service;

import com.service.model.SensitiveWord;
import com.service.model.SensitiveWordDto;
import com.service.repository.SensitiveWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SensitiveWordService {
    private final SensitiveWordRepository sensitiveWordRepository;

    @CacheEvict(value = "sensitiveWords", allEntries = true)
    public SensitiveWordDto create(@NonNull SensitiveWordDto dto) {
        SensitiveWord sensitiveWord = new SensitiveWord();
        sensitiveWord.setWord(dto.getWord());
        SensitiveWord saved = sensitiveWordRepository.save(sensitiveWord);
        return mapToDto(saved);
    }

    public Page<SensitiveWordDto> getAll(Pageable pageable) {
        return sensitiveWordRepository.findAll(pageable).map(this::mapToDto);
    }

    public List<SensitiveWordDto> getAll() {
        return sensitiveWordRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public SensitiveWordDto getById(@NonNull Long id) {
        return sensitiveWordRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    @CacheEvict(value = "sensitiveWords", allEntries = true)
    public SensitiveWordDto update(@NonNull Long id, @NonNull SensitiveWordDto dto) {
        return sensitiveWordRepository.findById(id)
                .map(existingWord -> {
                    existingWord.setWord(dto.getWord());
                    SensitiveWord saved = sensitiveWordRepository.save(existingWord);
                    return mapToDto(saved);
                })
                .orElse(null);
    }

    @CacheEvict(value = "sensitiveWords", allEntries = true)
    public void delete(@NonNull Long id) {
        sensitiveWordRepository.deleteById(id);
    }

    private SensitiveWordDto mapToDto(SensitiveWord entity) {
        return SensitiveWordDto.builder()
                .id(entity.getId())
                .word(entity.getWord())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
