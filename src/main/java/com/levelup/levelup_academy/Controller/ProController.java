package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.DTO.ProDTO;
import com.levelup.levelup_academy.Model.Pro;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Service.ProService;
import com.levelup.levelup_academy.Service.StatisticProService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/pro")
@RequiredArgsConstructor
public class ProController {
    private final ProService proService;
    private final StatisticProService statisticProService;

    //GET
    @GetMapping("/get/{moderatorId}")
    public ResponseEntity getAllPro(@PathVariable Integer moderatorId) {
        return ResponseEntity.status(200).body(proService.getAllPro(moderatorId));
    }

    //get pro player by id
    @GetMapping("/get/{moderatorId}/{proId}")
     public ResponseEntity getPro(@PathVariable Integer moderatorId,@PathVariable Integer proId){
        return ResponseEntity.status(200).body(proService.getPro(moderatorId, proId));
     }

    //Register pro player
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registerTrainer(
            @RequestPart("pro") ProDTO proDTO,
            @RequestPart("file") MultipartFile file) {
        proService.registerPro(proDTO, file);
        return ResponseEntity.ok("pro player registered successfully with PDF uploaded");
    }

    //Edit
    @PutMapping("/edit/{proId}")
    public ResponseEntity EditProAccount(@PathVariable Integer proId, @RequestBody @Valid ProDTO proDTO) {
        proService.edit(proId, proDTO);
        return ResponseEntity.ok("Pro player information updated successfully");
    }

    // Endpoint to delete Pro player by ID
    @DeleteMapping("/delete/{proId}")
    public ResponseEntity deleteProPlayer(@PathVariable Integer proId) {
        proService.delete(proId);
        return ResponseEntity.ok("Your account have been deleted successfully.");

    }

    @PostMapping("/approve/{adminId}/{proId}")
    public ResponseEntity<String> approvePro(@PathVariable Integer adminId, @PathVariable Integer proId) {
        proService.approveProByAdmin(adminId, proId);
        return ResponseEntity.ok("The professional player has been approved.");
    }
    @PutMapping("/reject/{adminId}/{proId}")
    public ResponseEntity<String> rejectPro(@PathVariable Integer adminId, @PathVariable Integer proId) {
        proService.rejectProByAdmin(adminId, proId);
        return ResponseEntity.ok("The professional player has been rejected and deleted.");
    }

    @GetMapping("/{id}/cv")
    public ResponseEntity<byte[]> downloadProCv(@PathVariable Integer id) {
        byte[] cvContent = proService.downloadProCv(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition
                .builder("attachment")
                .filename("pro_pdf_" + id + ".pdf")
                .build());

        return new ResponseEntity<>(cvContent, headers, HttpStatus.OK);
    }


    // Accept contract
    @PutMapping("/accept/{proId}/{contractId}")
    public ResponseEntity acceptContract(@PathVariable Integer proId,@PathVariable Integer contractId) {
        contractService.acceptContract(proId, contractId);
        return ResponseEntity.ok("Contract accepted.");
    }

    // Reject contrat
    @PutMapping("/reject/{proId}/{contractId}")
    public ResponseEntity rejectContract(@PathVariable Integer proId,@PathVariable Integer contractId) {
        contractService.rejectContract(proId, contractId);
        return ResponseEntity.ok("Contract rejected.");
    }

    @PostMapping("/expireAccount")
    public ResponseEntity expireAccount(){
        proService.expireAccount();
        return ResponseEntity.status(200).body(new ApiResponse("Expired Pro accounts have been processed."));
    }

}