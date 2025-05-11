package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Contract;
import com.levelup.levelup_academy.Model.Pro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract,Integer> {
    Contract findContractById(Integer id);

    List<Contract> findAllByPro(Pro pro);

   List<Contract> findByStartDateBefore(LocalDate startDateBefore);
}
