package com.levelup.levelup_academy.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Class name can not be empty")
    @Column(columnDefinition = "varchar(40) not null")
    private String name;
    @Column(columnDefinition = "DATE")
    private LocalDate startDate;
    @Column(columnDefinition = "DATE")
    private LocalDate endDate;
    @Column(columnDefinition = "int not null")
    private Integer availableSets;
    @Column(columnDefinition = "varchar(20) not null")
    private String time;
    @NotEmpty(message = "Game can not be empty")
    @Column(columnDefinition = "varchar(30) not null")
    private String games;
    @ManyToOne
    @JoinColumn(name = "trainer_id" , referencedColumnName = "id")
    @JsonIgnore
    private Trainer trainer;
    @ManyToOne
    @JoinColumn(name = "game_id" , referencedColumnName = "id")
    @JsonIgnore
    private Game game;


}
