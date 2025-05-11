package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthRepository extends JpaRepository<User,Integer> {
    User findUserById(Integer id);

    User findUserByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername( String username);
}
