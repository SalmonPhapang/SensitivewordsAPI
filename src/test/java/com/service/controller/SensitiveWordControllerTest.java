package com.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.model.SensitiveWord;
import com.service.service.SensitiveWordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SensitiveWordController.class)
class SensitiveWordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SensitiveWordService sensitiveWordService;

    @Autowired
    private ObjectMapper objectMapper;

    private SensitiveWord mockWord;

    @BeforeEach
    void setUp() {
        mockWord = new SensitiveWord();
        mockWord.setId(1L);
        mockWord.setWord("SELECT");
    }

    @Test
    void testCreate() throws Exception {
        when(sensitiveWordService.create(any(SensitiveWord.class))).thenReturn(mockWord);

        mockMvc.perform(post("/api/v1/sensitive-words")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockWord)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.word").value("SELECT"));
    }

    @Test
    void testGetAll() throws Exception {
        when(sensitiveWordService.getAll()).thenReturn(Arrays.asList(mockWord));

        mockMvc.perform(get("/api/v1/sensitive-words"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].word").value("SELECT"));
    }

    @Test
    void testGetById() throws Exception {
        when(sensitiveWordService.getById(1L)).thenReturn(mockWord);

        mockMvc.perform(get("/api/v1/sensitive-words/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.word").value("SELECT"));
    }

    @Test
    void testGetById_NotFound() throws Exception {
        when(sensitiveWordService.getById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/sensitive-words/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdate() throws Exception {
        when(sensitiveWordService.update(eq(1L), any(SensitiveWord.class))).thenReturn(mockWord);

        mockMvc.perform(put("/api/v1/sensitive-words/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockWord)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.word").value("SELECT"));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/sensitive-words/1"))
                .andExpect(status().isNoContent());
    }
}
