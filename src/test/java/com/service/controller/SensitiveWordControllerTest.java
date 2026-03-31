package com.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.config.JwtAuthenticationFilter;
import com.service.model.SensitiveWordDto;
import com.service.repository.UserRepository;
import com.service.service.JwtService;
import com.service.service.SensitiveWordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SensitiveWordController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:AppProps.properties")
class SensitiveWordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SensitiveWordService sensitiveWordService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationProvider authenticationProvider;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private SensitiveWordDto mockDto;

    @BeforeEach
    void setUp() {
        mockDto = SensitiveWordDto.builder()
                .id(1L)
                .word("SELECT")
                .build();
    }

    @Test
    void testCreate() throws Exception {
        when(sensitiveWordService.create(any(SensitiveWordDto.class))).thenReturn(mockDto);

        mockMvc.perform(post("/api/v1/sensitive-words")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.word").value("SELECT"));
    }

    @Test
    void testGetAllPaginated() throws Exception {
        Page<SensitiveWordDto> page = new PageImpl<>(Arrays.asList(mockDto));
        when(sensitiveWordService.getAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/sensitive-words"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].word").value("SELECT"));
    }

    @Test
    void testGetAllList() throws Exception {
        when(sensitiveWordService.getAll()).thenReturn(Arrays.asList(mockDto));

        mockMvc.perform(get("/api/v1/sensitive-words/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].word").value("SELECT"));
    }

    @Test
    void testGetById() throws Exception {
        when(sensitiveWordService.getById(1L)).thenReturn(mockDto);

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
        when(sensitiveWordService.update(eq(1L), any(SensitiveWordDto.class))).thenReturn(mockDto);

        mockMvc.perform(put("/api/v1/sensitive-words/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockDto)))
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
