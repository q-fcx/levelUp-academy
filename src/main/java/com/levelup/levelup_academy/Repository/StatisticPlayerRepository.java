package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Player;
import com.levelup.levelup_academy.Model.Pro;
import com.levelup.levelup_academy.Model.StatisticChild;
import com.levelup.levelup_academy.Model.StatisticPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticPlayerRepository extends JpaRepository<StatisticPlayer,Integer> {
    StatisticPlayer findStatisticPlayerById(Integer id);
    StatisticPlayer findByPlayer_Id(Integer playerId);
    List<StatisticPlayer> findAll();
    List<StatisticPlayer> findStatisticPlayerByWinGame( Integer winGame);

}
