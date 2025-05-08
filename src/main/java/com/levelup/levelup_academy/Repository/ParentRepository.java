package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Integer> {
    Parent findParentById(Integer id);
}
