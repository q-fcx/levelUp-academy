package com.levelup.levelup_academy.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class StatisticPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "Integer not null")
    private Integer rate;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "Integer not null")
    private Integer winGame;

    @NotNull
    @Min(0)
    @Column(columnDefinition = "Integer not null")
    private Integer lossGame;

    @NotNull
    @Size(max = 50)
    @Column(columnDefinition = "varchar(50) not null")
    private String trophy;

    @NotNull
    @Size(max = 50)
    @Column(columnDefinition = "varchar(50) not null")
    private String field;

    @NotNull
    @Column(columnDefinition = "date not null")
    private LocalDate date;


    @OneToOne
//    @JoinColumn(name = "player_id", referencedColumnName = "id")
    @JsonIgnore
    @MapsId
    private Player player;

}
