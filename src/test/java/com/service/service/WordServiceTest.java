package com.service.service;

import com.service.model.SensitiveWord;
import com.service.model.WordCheckResponse;
import com.service.repository.SensitiveWordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WordServiceTest {

    @Mock
    private SensitiveWordRepository sensitiveWordRepository;

    @InjectMocks
    private WordService wordService;

    private List<SensitiveWord> mockSensitiveWords;

    @BeforeEach
    void setUp() {
        SensitiveWord word1 = new SensitiveWord();
        word1.setId(1L);
        word1.setWord("SELECT");

        SensitiveWord word2 = new SensitiveWord();
        word2.setId(2L);
        word2.setWord("DROP");

        mockSensitiveWords = Arrays.asList(word1, word2);
    }

    @Test
    void testCheckAndFilter_WithSensitiveWords() {
        when(sensitiveWordRepository.findAll()).thenReturn(mockSensitiveWords);

        String text = "Please SELECT * FROM users and then DROP table";
        WordCheckResponse response = wordService.checkAndFilter(text);

        assertTrue(response.isHasSensitiveWords());
        assertEquals(2, response.getFoundWords().size());
        assertTrue(response.getFoundWords().contains("SELECT"));
        assertTrue(response.getFoundWords().contains("DROP"));
        assertEquals("Please ****** * FROM users and then **** table", response.getFilteredText());
    }

    @Test
    void testCheckAndFilter_NoSensitiveWords() {
        when(sensitiveWordRepository.findAll()).thenReturn(mockSensitiveWords);

        String text = "This is a clean message";
        WordCheckResponse response = wordService.checkAndFilter(text);

        assertFalse(response.isHasSensitiveWords());
        assertTrue(response.getFoundWords().isEmpty());
        assertEquals(text, response.getFilteredText());
    }

    @Test
    void testCheckAndFilter_EmptyText() {
        WordCheckResponse response = wordService.checkAndFilter("");

        assertFalse(response.isHasSensitiveWords());
        assertTrue(response.getFoundWords().isEmpty());
        assertEquals("", response.getFilteredText());
    }

    @Test
    void testCheckAndFilter_NullText() {
        WordCheckResponse response = wordService.checkAndFilter(null);

        assertFalse(response.isHasSensitiveWords());
        assertTrue(response.getFoundWords().isEmpty());
        assertNull(response.getFilteredText());
    }

    @Test
    void testCheckAndFilter_CaseInsensitive() {
        when(sensitiveWordRepository.findAll()).thenReturn(mockSensitiveWords);

        String text = "select all from users";
        WordCheckResponse response = wordService.checkAndFilter(text);

        assertTrue(response.isHasSensitiveWords());
        assertEquals(1, response.getFoundWords().size());
        assertTrue(response.getFoundWords().contains("SELECT"));
        assertEquals("****** all from users", response.getFilteredText());
    }

    @Test
    void testCheckAndFilter_WholeWordOnly() {
        when(sensitiveWordRepository.findAll()).thenReturn(mockSensitiveWords);

        String text = "Selection of words";
        WordCheckResponse response = wordService.checkAndFilter(text);

        assertFalse(response.isHasSensitiveWords());
        assertEquals(text, response.getFilteredText());
    }
}
