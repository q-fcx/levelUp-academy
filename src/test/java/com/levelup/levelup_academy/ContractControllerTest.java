package com.levelup.levelup_academy;

import com.levelup.levelup_academy.Controller.ContractController;
import com.levelup.levelup_academy.DTO.ContractDTO;
import com.levelup.levelup_academy.Model.Contract;
import com.levelup.levelup_academy.Model.Moderator;
import com.levelup.levelup_academy.Model.Pro;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Service.ContractService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.TestConfiguration;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;


import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(ContractController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ContractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContractService contractService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ContractService contractService() {
            return Mockito.mock(ContractService.class);
        }
    }

    @Test
    public void testGetAllContractsByModeratorId() throws Exception {
        Pro pro = new Pro();
        pro.setId(10);

        Moderator moderator = new Moderator();
        moderator.setId(1);

        Contract mockContract = new Contract();
        mockContract.setId(1);
        mockContract.setTeam("Alpha Team");
        mockContract.setEmail("alpha@team.com");
        mockContract.setCommercialRegister(123456);
        mockContract.setGame("Valorant");
        mockContract.setStartDate(LocalDate.of(2025, 1, 1));
        mockContract.setEndDate(LocalDate.of(2025, 12, 31));
        mockContract.setAmount(2000.0);
        mockContract.setPro(pro);
        mockContract.setModerator(moderator);

        List<Contract> contractList = List.of(mockContract);

        Mockito.when(contractService.getAllContract(1)).thenReturn(contractList);

        User mockUser = new User();
        mockUser.setId(1);
        TestingAuthenticationToken auth = new TestingAuthenticationToken(mockUser, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        mockMvc.perform(get("/api/v1/contract/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].team").value("Alpha Team"))
                .andExpect(jsonPath("$[0].email").value("alpha@team.com"))
                .andExpect(jsonPath("$[0].commercialRegister").value(123456))
                .andExpect(jsonPath("$[0].game").value("Valorant"))
                .andExpect(jsonPath("$[0].amount").value(2000.0));

        SecurityContextHolder.clearContext();
    }

    @Test
    public void testAddContract() throws Exception {


        User mockModerator = new User();
        mockModerator.setId(1);

        Mockito.doNothing().when(contractService).addContract(Mockito.eq(1), Mockito.any(ContractDTO.class));


        String contractJson = """
        {
            "id": 1,
            "team": "Alpha Team",
            "email": "alpha@team.com",
            "commercialRegister": 123456,
            "game": "Valorant",
            "startDate": "2025-01-01",
            "endDate": "2025-12-31",
            "amount": 2000.0,
            "proId": 10
        }
    """;


        TestingAuthenticationToken authenticationToken = new TestingAuthenticationToken(mockModerator, null);
        authenticationToken.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        mockMvc.perform(post("/api/v1/contract/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contractJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Contract added and email sent successfully."));



        SecurityContextHolder.clearContext();
    }



}
