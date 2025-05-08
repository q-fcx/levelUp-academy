package com.levelup.levelup_academy.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate bookDate;

    @OneToOne(mappedBy = "booking",cascade = CascadeType.ALL)
    private Player player;

    @OneToOne(mappedBy = "booking",cascade = CascadeType.ALL)
    private Parent parent;

}
