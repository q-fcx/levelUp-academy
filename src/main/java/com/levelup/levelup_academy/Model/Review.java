package com.levelup.levelup_academy.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String comment;
    private LocalDate date;
    private Integer rate;

    @ManyToOne
    @JoinColumn(name = "trainer_review_id", referencedColumnName = "id")
    @JsonIgnore
    private Trainer trainer;

}
