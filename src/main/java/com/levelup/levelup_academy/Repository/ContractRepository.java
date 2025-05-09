package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract,Integer> {
    Contract findContractById(Integer id);
}
