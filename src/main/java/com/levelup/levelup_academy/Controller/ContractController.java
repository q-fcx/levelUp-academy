package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.DTO.ContractDTO;
import com.levelup.levelup_academy.Service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/contract")
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;

    @PostMapping("/add")
    public ResponseEntity<String> addContract(@RequestBody @Valid ContractDTO contractDTO) {
        contractService.addContract(contractDTO);
        return ResponseEntity.ok("Contract added and email sent successfully.");
    }

    @GetMapping("/get-all-contract/{proId}")
    public ResponseEntity getContractForPro(@PathVariable Integer proId){
        return ResponseEntity.status(200).body(contractService.getAllContract(proId));
    }

}
