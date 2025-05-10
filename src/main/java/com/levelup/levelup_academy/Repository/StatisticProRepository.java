package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Pro;
import com.levelup.levelup_academy.Model.StatisticChild;
import com.levelup.levelup_academy.Model.StatisticPro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticProRepository extends JpaRepository<StatisticPro,Integer> {
    StatisticPro findStatisticProById(Integer id);
    StatisticPro findByPro_Id(Integer professionalId);
    List<StatisticPro> findByPro_Trainer_Id(Integer trainerId);
    List<StatisticPro> findAll();
    List<StatisticPro> findStatisticProByWinGame( Integer winGame);
}
