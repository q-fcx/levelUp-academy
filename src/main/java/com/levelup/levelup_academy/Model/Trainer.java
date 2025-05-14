package com.levelup.levelup_academy.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor

public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonIgnore
    private String cvPath;
    @JsonIgnore
    private Boolean isAvailable = false;
    @JsonIgnore
    private Boolean isApproved=false;

//    @Lob
//    private byte[] cv;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "trainer")
    private Set<Session> sessions;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "trainer")
    private Set<Review> reviews;


}
