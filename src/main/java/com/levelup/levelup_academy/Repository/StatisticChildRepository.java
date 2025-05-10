package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Child;
import com.levelup.levelup_academy.Model.StatisticChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticChildRepository extends JpaRepository<StatisticChild,Integer> {
    StatisticChild findStatisticChildByChild(Child child);
    StatisticChild findTopByOrderByTrophyDesc();
    List<StatisticChild> findTop5ByChild_GameOrderByRateDesc(String game);
}
