package com.levelup.levelup_academy.DTOOut;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ParentDTOOut {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<ChildDTOOut> children;
}
