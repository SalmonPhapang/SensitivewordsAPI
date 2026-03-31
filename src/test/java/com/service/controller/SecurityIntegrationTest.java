package com.service.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnUnauthorizedWhenAccessingSecuredSensitiveWordsEndpointWithoutToken() throws Exception {
        mockMvc.perform(get("/api/v1/sensitive-words"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnUnauthorizedWhenAccessingSecuredWordsCheckEndpointWithoutToken() throws Exception {
        mockMvc.perform(post("/api/v1/words/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"text\":\"hello world\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldAllowAccessToAuthRegisterWithoutToken() throws Exception {
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"password\":\"testpass\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAllowAccessToAuthAuthenticateWithoutToken() throws Exception {
        mockMvc.perform(post("/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"password\":\"testpass\"}"))
                .andExpect(status().isOk());
    }
}
