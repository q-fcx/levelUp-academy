package com.levelup.levelup_academy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.levelup.levelup_academy.DTO.PlayerDTO;
import com.levelup.levelup_academy.DTOOut.PlayerDTOOut;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Service.PlayerService;
import com.levelup.levelup_academy.Controller.PlayerController;

import com.levelup.levelup_academy.Service.StatisticPlayerService;

import org.junit.jupiter.api.Test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;

@WebMvcTest(PlayerController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private StatisticPlayerService statisticPlayerService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public PlayerService playerService() {
            return Mockito.mock(PlayerService.class);
        }

        @Bean
        public StatisticPlayerService statisticPlayerService() {
            return Mockito.mock(StatisticPlayerService.class);
        }
    }

    @Test
    public void testGetAllPlayers() throws Exception {

        PlayerDTOOut mockPlayer = new PlayerDTOOut(
                "Abdullah",
                "Abdullah",
                "Ali",
                "Abdullah@gmail.com");

        List<PlayerDTOOut> mockPlayers = List.of(mockPlayer);
        Mockito.when(playerService.getAllPlayers()).thenReturn(mockPlayers);

        mockMvc.perform(get("/api/v1/player/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testDeletePlayer() throws Exception {
        Mockito.doNothing().when(playerService).deletePlayer(1);

        mockMvc.perform(delete("/api/v1/player/delete")
                        .param("playerId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Player deleted successfully"));
    }




}
