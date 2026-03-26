package com.service.service;

import com.service.model.SensitiveWord;
import com.service.repository.SensitiveWordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SensitiveWordServiceTest {

    @Mock
    private SensitiveWordRepository sensitiveWordRepository;

    @InjectMocks
    private SensitiveWordService sensitiveWordService;

    private SensitiveWord mockWord;

    @BeforeEach
    void setUp() {
        mockWord = new SensitiveWord();
        mockWord.setId(1L);
        mockWord.setWord("BADWORD");
    }

    @Test
    void testCreate() {
        when(sensitiveWordRepository.save(any(SensitiveWord.class))).thenReturn(mockWord);

        SensitiveWord result = sensitiveWordService.create(new SensitiveWord());

        assertNotNull(result);
        assertEquals("BADWORD", result.getWord());
        verify(sensitiveWordRepository, times(1)).save(any(SensitiveWord.class));
    }

    @Test
    void testGetAll() {
        List<SensitiveWord> words = Arrays.asList(mockWord);
        when(sensitiveWordRepository.findAll()).thenReturn(words);

        List<SensitiveWord> result = sensitiveWordService.getAll();

        assertEquals(1, result.size());
        assertEquals("BADWORD", result.get(0).getWord());
    }

    @Test
    void testGetById() {
        when(sensitiveWordRepository.findById(1L)).thenReturn(Optional.of(mockWord));

        SensitiveWord result = sensitiveWordService.getById(1L);

        assertNotNull(result);
        assertEquals("BADWORD", result.getWord());
    }

    @Test
    void testUpdate() {
        when(sensitiveWordRepository.findById(1L)).thenReturn(Optional.of(mockWord));
        when(sensitiveWordRepository.save(any(SensitiveWord.class))).thenReturn(mockWord);

        SensitiveWord updatedWord = new SensitiveWord();
        updatedWord.setWord("NEWWORD");

        SensitiveWord result = sensitiveWordService.update(1L, updatedWord);

        assertNotNull(result);
        assertEquals("BADWORD", result.getWord()); // mocked to return mockWord which we updated
        verify(sensitiveWordRepository).save(mockWord);
    }

    @Test
    void testDelete() {
        doNothing().when(sensitiveWordRepository).deleteById(1L);

        sensitiveWordService.delete(1L);

        verify(sensitiveWordRepository, times(1)).deleteById(1L);
    }
}
