package com.levelup.levelup_academy.DTOOut;

import com.levelup.levelup_academy.Model.Session;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class TrainerDTOOut {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Session> sessions;
}
