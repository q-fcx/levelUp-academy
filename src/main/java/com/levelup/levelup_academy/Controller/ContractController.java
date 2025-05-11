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

    @GetMapping("/get/{proId}")
    public ResponseEntity gatAllContract(@PathVariable Integer proId){
        return ResponseEntity.status(200).body(contractService.getAllContract(proId));
    }

    @PostMapping("/add/{moderatorId}")
    public ResponseEntity<String> addContract(@PathVariable Integer moderatorId,@RequestBody @Valid ContractDTO contractDTO) {
        contractService.addContract(moderatorId,contractDTO);
        return ResponseEntity.ok("Contract added and email sent successfully.");
    }

    @GetMapping("/get-all-contract/{proId}")
    public ResponseEntity getContractForPro(@PathVariable Integer proId){
        return ResponseEntity.status(200).body(contractService.getAllContract(proId));
    }

}
