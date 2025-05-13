package com.levelup.levelup_academy.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Moderator {
    @Id
    private Integer id;

    @OneToOne
    @JsonIgnore
    @MapsId
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "moderator")
    private List<Contract> contract;


}
