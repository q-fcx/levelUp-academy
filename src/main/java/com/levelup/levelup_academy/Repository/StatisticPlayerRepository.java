package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Player;
import com.levelup.levelup_academy.Model.Pro;
import com.levelup.levelup_academy.Model.StatisticPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticPlayerRepository extends JpaRepository<StatisticPlayer,Integer> {
    StatisticPlayer findStatisticByPlayer(Player player);

}
