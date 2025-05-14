package com.levelup.levelup_academy.DTOOut;

import com.levelup.levelup_academy.Model.StatisticPro;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProDTOOut {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private StatisticPro statistics;
}
