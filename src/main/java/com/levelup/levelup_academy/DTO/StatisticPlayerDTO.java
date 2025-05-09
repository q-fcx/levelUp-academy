package com.levelup.levelup_academy.DTO;

import com.levelup.levelup_academy.Model.Player;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class StatisticPlayerDTO {
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

    private Player player;

}
