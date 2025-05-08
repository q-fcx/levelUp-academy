package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildRepository extends JpaRepository<Child, Integer> {
    Child findChildById(Integer id);
}
