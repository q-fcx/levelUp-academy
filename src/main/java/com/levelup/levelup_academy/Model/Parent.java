package com.levelup.levelup_academy.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;
@Data
@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @OneToOne
    @JsonIgnore
    private User user;
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    private List<Child> children;

    @OneToOne
    @JsonIgnore
    private Booking booking;

}
