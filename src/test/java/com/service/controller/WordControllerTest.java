package com.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.model.WordCheckRequest;
import com.service.model.WordCheckResponse;
import com.service.service.WordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WordController.class)
class WordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WordService wordService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCheckWords() throws Exception {
        WordCheckResponse response = WordCheckResponse.builder()
                .hasSensitiveWords(true)
                .foundWords(Collections.singletonList("SELECT"))
                .filteredText("*** * FROM users")
                .build();

        when(wordService.checkAndFilter(anyString())).thenReturn(response);

        WordCheckRequest request = new WordCheckRequest();
        request.setText("SELECT * FROM users");

        mockMvc.perform(post("/api/v1/words/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hasSensitiveWords").value(true))
                .andExpect(jsonPath("$.filteredText").value("*** * FROM users"));
    }

    @Test
    void testHealth() throws Exception {
        mockMvc.perform(get("/api/v1/words/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Sensitive Words API is running"));
    }
}
