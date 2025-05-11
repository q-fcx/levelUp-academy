package com.levelup.levelup_academy.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(40) not null")
    @NotEmpty(message = "Name can not be empty")
    private String name;
    @Column(columnDefinition = "int not null")
    private Integer age;
    @Column(columnDefinition = "varchar(40) not null")
    @NotEmpty(message = "Category can not be empty")
    private String category;



    @OneToMany(cascade = CascadeType.ALL,mappedBy = "game")
    private Set<Session> sessions;

}
