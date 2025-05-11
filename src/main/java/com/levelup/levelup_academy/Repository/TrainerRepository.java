package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Session;
import com.levelup.levelup_academy.Model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer,Integer> {
    Trainer findTrainerById(Integer id);

    List<Trainer> findAllBySessions(Set<Session> sessions);

}
