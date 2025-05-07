package com.levelup.levelup_academy.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.io.File;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor

public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private File cv;
    @NotEmpty(message = "Game can not empty")
    @Column(columnDefinition = "varchar(40) not null")
    @Pattern(regexp = "^(FC25|CALLOFDUTY|OVERWATCH|FATALFURY|FORTNITE|ROCKETLEAUGE)$", message = "Game must be FC25, CALLOFDUTY, OVERWATCH, FATALFURY, FORTNITE or ROCKETLEAUGE only")
    private String game;
    private Boolean isAvailable = false;
}
