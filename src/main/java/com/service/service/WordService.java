package com.service.service;

import com.service.model.SensitiveWord;
import com.service.model.WordCheckResponse;
import com.service.repository.SensitiveWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WordService {
    private final SensitiveWordRepository sensitiveWordRepository;

    @Cacheable("sensitiveWords")
    public List<String> getSensitiveWords() {
        return sensitiveWordRepository.findAll().stream()
                .map(SensitiveWord::getWord)
                .collect(Collectors.toList());
    }

    public WordCheckResponse checkAndFilter(String text) {
        if (text == null || text.isBlank()) {
            return WordCheckResponse.builder()
                    .hasSensitiveWords(false)
                    .foundWords(new ArrayList<>())
                    .filteredText(text)
                    .build();
        }

        List<String> sensitiveWords = getSensitiveWords();

        List<String> found = new ArrayList<>();
        String filtered = text;

        for (String word : sensitiveWords) {
            // Use whole-word matching with \b and handle special characters in the word
            String escapedWord = Pattern.quote(word);
            String regex = "(?i)\\b" + escapedWord + "\\b";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(filtered);

            if (matcher.find()) {
                found.add(word);
                // Replace with stars matching the word's length
                String stars = "*".repeat(word.length());
                filtered = matcher.replaceAll(stars);
            }
        }

        return WordCheckResponse.builder()
                .hasSensitiveWords(!found.isEmpty())
                .foundWords(found)
                .filteredText(filtered)
                .build();
    }
}
