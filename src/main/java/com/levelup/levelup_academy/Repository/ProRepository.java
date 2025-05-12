package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Pro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProRepository extends JpaRepository<Pro,Integer> {
    Pro findProById(Integer id);

    List<Pro> findByIsApproved(Boolean isApproved);

}
