package com.levelup.levelup_academy.DTOOut;

import com.levelup.levelup_academy.Model.StatisticPlayer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerDTOOut {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private StatisticPlayer statistics;
}
