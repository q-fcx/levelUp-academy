package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Player;
import com.levelup.levelup_academy.Model.Pro;
import com.levelup.levelup_academy.Model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics,Integer> {
    Statistics findStatisticByPlayer(Player player);

    Statistics findStatisticsByPro(Pro pro);
}
