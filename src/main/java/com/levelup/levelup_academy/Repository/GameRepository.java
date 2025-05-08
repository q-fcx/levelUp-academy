package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game,Integer> {
    Game findGameById(Integer id);
}
