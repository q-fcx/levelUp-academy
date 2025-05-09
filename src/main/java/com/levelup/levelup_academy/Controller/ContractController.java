package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.DTO.ContractDTO;
import com.levelup.levelup_academy.Service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
