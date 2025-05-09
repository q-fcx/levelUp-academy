package com.levelup.levelup_academy.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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

    @NotEmpty(message = "Status cannot be empty")
    @Pattern(regexp = "^(PENDING|ACTIVE)$", message = "Booking must be PENDING OR ACTIVE only")
    @Column(columnDefinition = "varchar(20) not null")
    private String status;

    @ManyToOne
    @JoinColumn(name = "session_id", referencedColumnName = "id")
    @JsonIgnore
    private Session session;

}
