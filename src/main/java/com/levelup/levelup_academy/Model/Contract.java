package com.levelup.levelup_academy.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private Boolean proStatus = false;

    private Boolean moderatorStatus = false;


    @OneToOne
    @JoinColumn
    @JsonIgnore
    private Pro pro;

    private String contractStatus="pending";

    @ManyToOne
    @JoinColumn(name = "moderator_id")
    @JsonIgnore
    private Moderator moderator;
}
