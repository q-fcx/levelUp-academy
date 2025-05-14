package com.levelup.levelup_academy.DTOOut;

import com.levelup.levelup_academy.Model.StatisticChild;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChildDTOOut {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private StatisticChild statistics;
}
