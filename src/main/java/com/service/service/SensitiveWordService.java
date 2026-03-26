package com.service.service;

import com.service.model.SensitiveWord;
import com.service.repository.SensitiveWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SensitiveWordService {
    private final SensitiveWordRepository sensitiveWordRepository;

    @CacheEvict(value = "sensitiveWords", allEntries = true)
    public SensitiveWord create(@NonNull SensitiveWord sensitiveWord) {
        return sensitiveWordRepository.save(sensitiveWord);
    }

    public List<SensitiveWord> getAll() {
        return sensitiveWordRepository.findAll();
    }

    public SensitiveWord getById(@NonNull Long id) {
        return sensitiveWordRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = "sensitiveWords", allEntries = true)
    public SensitiveWord update(@NonNull Long id, SensitiveWord sensitiveWord) {
        return sensitiveWordRepository.findById(id)
                .map(existingWord -> {
                    existingWord.setWord(sensitiveWord.getWord());
                    return sensitiveWordRepository.save(existingWord);
                })
                .orElse(null);
    }

    @CacheEvict(value = "sensitiveWords", allEntries = true)
    public void delete(@NonNull Long id) {
        sensitiveWordRepository.deleteById(id);
    }
}
