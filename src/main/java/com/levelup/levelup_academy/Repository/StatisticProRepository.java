package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Pro;
import com.levelup.levelup_academy.Model.StatisticPro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticProRepository extends JpaRepository<StatisticPro,Integer> {
    StatisticPro findStatisticProByPro(Pro pro);
    StatisticPro findTopByOrderByTrophyDesc();
    List<StatisticPro> findTop5ByPro_GameOrderByRateDesc(String game);
}
