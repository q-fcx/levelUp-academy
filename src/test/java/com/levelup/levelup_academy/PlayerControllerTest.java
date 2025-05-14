//package com.levelup.levelup_academy;
//
//import com.levelup.levelup_academy.DTOOut.PlayerDTOOut;
//
//import com.levelup.levelup_academy.Model.User;
//import com.levelup.levelup_academy.Service.PlayerService;
//import com.levelup.levelup_academy.Controller.PlayerController;
//
//import com.levelup.levelup_academy.Service.StatisticPlayerService;
//
//import org.junit.jupiter.api.Test;
//
//;
//
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//
//import org.springframework.test.web.servlet.MockMvc;
//
//;
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasSize;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(PlayerController.class)
//@AutoConfigureMockMvc(addFilters = false)
//public class PlayerControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private PlayerService playerService;
//
//    @Autowired
//    private StatisticPlayerService statisticPlayerService;
//
//    @TestConfiguration
//    static class TestConfig {
//        @Bean
//        public PlayerService playerService() {
//            return Mockito.mock(PlayerService.class);
//        }
//
//        @Bean
//        public StatisticPlayerService statisticPlayerService() {
//            return Mockito.mock(StatisticPlayerService.class);
//        }
//    }
//    private void setAuthenticationPrincipal(User user) {
//        var auth = Mockito.mock(org.springframework.security.core.Authentication.class);
//        Mockito.when(auth.getPrincipal()).thenReturn(user);
//
//        var securityContext = Mockito.mock(org.springframework.security.core.context.SecurityContext.class);
//        Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
//
//        org.springframework.security.core.context.SecurityContextHolder.setContext(securityContext);
//    }
//
//    @Test
//    public void testGetAllPlayers() throws Exception {
//        PlayerDTOOut mockPlayer = new PlayerDTOOut(
//                "Abdullah",
//                "Abdullah",
//                "Ali",
//                "Abdullah@gmail.com");
//
//        List<PlayerDTOOut> mockPlayers = List.of(mockPlayer);
//
//
//        // Mock service
//        Mockito.when(playerService.getAllPlayers(1)).thenReturn(mockPlayers);
//
//        // Simulate a principal with ID 1 using Security context mocking
//        User mockUser = new User();
//        mockUser.setId(1);
//        mockUser.setUsername("moderator1");
//        mockUser.setPassword("pass");
//        mockUser.setRole("MODERATOR");
//        mockUser.setFirstName("Mod");
//        mockUser.setLastName("Erator");
//        mockUser.setEmail("mod@example.com");
//        setAuthenticationPrincipal(mockUser);
//
//        mockMvc.perform(get("/api/v1/player/get")
//                        .principal(() -> "moderator1") // not enough alone, for full User injection see below
//                        .requestAttr("org.springframework.security.core.annotation.AuthenticationPrincipal", mockUser))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)));
//    }
//    @Test
//    public void testDeletePlayer() throws Exception {
//        User mockUser = new User();
//        mockUser.setId(1);
//
//        setAuthenticationPrincipal(mockUser);
//
//        Mockito.doNothing().when(playerService).deletePlayer(1);
//
//        mockMvc.perform(delete("/api/v1/player/delete"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Player deleted successfully"));
//    }
//
//
//
//
//
//}
