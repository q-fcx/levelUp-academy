package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session,Integer> {
    Session findSessionById(Integer id);

    Session findSessionsByTrainer_Id(Integer trainerId);
}
