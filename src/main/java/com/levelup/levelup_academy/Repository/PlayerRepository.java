package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Player;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {
    Player findPlayerById(Integer id);

}
