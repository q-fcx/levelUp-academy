package com.levelup.levelup_academy.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
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
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "session can not be null")
    @PositiveOrZero(message = "Session cannot be less thant zero")
    @Column(columnDefinition = "int not null")
    private Integer sessionCount;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;

    @NotEmpty(message = "package type can not be empty")
    @Pattern(regexp = "^(BASIC|STANDARD|PREMIUM)$", message = "Package type must be BASIC OR STANDARD or PREMIUM only")
    @Column(columnDefinition = "varchar(20) not null")
    private String packageType;
}
