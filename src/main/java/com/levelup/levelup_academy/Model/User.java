package com.levelup.levelup_academy.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Username can not be empty")
    @Size(min = 3,max = 30,message = "Username length must be between 3 and 30 characters")
    @Column(columnDefinition = "varchar(200) not null unique")
    private String username;
    @NotEmpty(message = "Password can not be empty")
    @Size(min = 8,max = 200,message = "Password length must be more than 8 character")
    @Column(columnDefinition = "varchar(220) not null")
    private String password;
    @Email
    @Column(columnDefinition = "varchar(200) not null unique")
    private String email;
    @NotEmpty(message = "firstName can not be empty")
    @Column(columnDefinition = "varchar(40) not null")
    private String firstName;
    @NotEmpty(message = "lastName can not be empty")
    @Column(columnDefinition = "varchar(40) not null")
    private String lastName;
    @Column(columnDefinition = "varchar(40) not null")
    @Pattern(regexp = "^(ADMIN|MODERATOR|PLAYER|PRO|PARENTS|TRAINER)$", message = "Role must be ADMIN, MODERATOR, PLAYER, PRO or PARENTS only")
    private String role;

    private Boolean iaApproved = false;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Trainer trainer;
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Player player;
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Pro pro;
    //Moderator
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Moderator moderator;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Booking booking;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Subscription> subscriptions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Review> reviews;






}
