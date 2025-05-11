package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Session;
import com.levelup.levelup_academy.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session,Integer> {
    Session findSessionById(Integer id);
}
