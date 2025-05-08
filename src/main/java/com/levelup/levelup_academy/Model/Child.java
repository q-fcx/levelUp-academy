package com.levelup.levelup_academy.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "firstName can not be empty")
    @Column(columnDefinition = "varchar(40) not null")
    private String firstName;

    @NotEmpty(message = "lastName can not be empty")
    @Column(columnDefinition = "varchar(40) not null")
    private String lastName;

    @NotNull(message = "age can not be empty")
    @Positive(message = "age must be positive number")
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @JsonIgnore
    private Parent parent;
}
