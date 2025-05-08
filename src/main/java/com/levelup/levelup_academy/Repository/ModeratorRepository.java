package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator,Integer> {
    Moderator findModeratorById(Integer id);
}
