package com.levelup.levelup_academy.DTOOut;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerDTOOut {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
}
