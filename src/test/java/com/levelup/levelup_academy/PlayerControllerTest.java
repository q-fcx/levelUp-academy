package com.levelup.levelup_academy;

import com.levelup.levelup_academy.DTOOut.PlayerDTOOut;

import com.levelup.levelup_academy.Service.PlayerService;
import com.levelup.levelup_academy.Controller.PlayerController;

import com.levelup.levelup_academy.Service.StatisticPlayerService;

import org.junit.jupiter.api.Test;

;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import org.springframework.test.web.servlet.MockMvc;

;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
