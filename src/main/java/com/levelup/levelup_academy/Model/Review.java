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
    @JoinColumn(name = "session_id", referencedColumnName = "id")
    @JsonIgnore
    private Session session;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "user_id1", referencedColumnName = "id")
//    @JsonIgnore
//    private User user1;

    @ManyToOne
    @JoinColumn(name = "trainer_id", referencedColumnName = "id")
    @JsonIgnore
    private Trainer trainer;
}
