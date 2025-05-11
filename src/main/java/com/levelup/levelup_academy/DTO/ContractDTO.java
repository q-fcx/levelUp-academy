package com.levelup.levelup_academy.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ContractDTO {
    private Integer id;
    @NotEmpty(message = "Team name can not be empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String team;
    @Email
    @Column(columnDefinition = "varchar(40) not null unique")
    private String email;
    @NotNull(message = "Commercial register can not be null")
    @Column(columnDefinition = "int not null")
    private Integer commercialRegister;
    @Column(columnDefinition = "varchar(40) not null")
    @NotEmpty(message = "Game name can not be empty")
    private String game;
    @Column(columnDefinition = "DATE")
    private LocalDate startDate;
    @Column(columnDefinition = "DATE")
    private LocalDate endDate;
    @Column(columnDefinition = "double not null")
    private Double amount;
    private Integer proId;


    @OneToOne
    @JoinColumn
    @JsonIgnore
    private Pro pro;

    @ManyToOne
    @JoinColumn(name = "moderator_id")
    @JsonIgnore
    private Moderator moderator;
}
