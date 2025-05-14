package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.DTO.ProDTO;
import com.levelup.levelup_academy.Model.Pro;
import com.levelup.levelup_academy.Model.StatisticPro;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.ContractRepository;
import com.levelup.levelup_academy.Service.ContractService;
import com.levelup.levelup_academy.Service.ProService;
import com.levelup.levelup_academy.Service.StatisticProService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/pro")
@RequiredArgsConstructor
public class ProController {
    private final ProService proService;
    private final StatisticProService statisticProService;
    private final ContractService contractService;

    //GET
    @GetMapping("/get")
    public ResponseEntity getAllPro(@AuthenticationPrincipal User moderatorId) {
        return ResponseEntity.status(200).body(proService.getAllPro(moderatorId.getModerator().getId()));
    }

    //get pro player by id
    @GetMapping("/get/{proId}")
     public ResponseEntity getPro(@AuthenticationPrincipal User moderatorId,@PathVariable Integer proId){
        return ResponseEntity.status(200).body(proService.getPro(moderatorId.getId(), proId));
     }

    //Register pro player
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registerTrainer(
            @RequestPart("pro") ProDTO proDTO,
            @RequestPart("file") MultipartFile file) {
        proService.registerPro(proDTO, file);
        return ResponseEntity.ok("pro player registered successfully with CV uploaded");
    }

    //Edit
    @PutMapping("/edit")
    public ResponseEntity EditProAccount(@AuthenticationPrincipal User proId, @RequestBody @Valid ProDTO proDTO) {
        proService.edit(proId.getId(), proDTO);
        return ResponseEntity.ok("Pro player information updated successfully");
    }

    // Endpoint to delete Pro player by ID
    @DeleteMapping("/delete")
    public ResponseEntity deleteProPlayer(@AuthenticationPrincipal User proId) {
        proService.delete(proId.getId());
        return ResponseEntity.ok("Your account have been deleted successfully.");

    }

    @GetMapping("/professional")
    public ResponseEntity<StatisticPro> getProfessionalStatistics(@AuthenticationPrincipal User professionalId) {
        return ResponseEntity.ok(proService.getMyStatisticsByProfessionalId(professionalId.getId()));
    }
    @PostMapping("/approve/{proId}")
    public ResponseEntity approvePro(@AuthenticationPrincipal User adminId, @PathVariable Integer proId) {
        proService.approveProByAdmin(adminId.getId(), proId);
        return ResponseEntity.ok("The professional player has been approved.");
    }
    @PutMapping("/reject/{proId}")
    public ResponseEntity rejectPro(@AuthenticationPrincipal User adminId, @PathVariable Integer proId) {
        proService.rejectProByAdmin(adminId.getId(), proId);
        return ResponseEntity.ok("The professional player has been rejected and deleted.");
    }

    @GetMapping("/cv/{proId}")
    public ResponseEntity<byte[]> downloadProCv(@AuthenticationPrincipal User moderatorId,@PathVariable Integer proId) {
        byte[] cvContent = proService.downloadProPDF(moderatorId.getModerator().getId(),proId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition
                .builder("attachment")
                .filename("pro_pdf_" + proId + ".pdf")
                .build());

        return new ResponseEntity<>(cvContent, headers, HttpStatus.OK);
    }


    // Accept contract
    @PutMapping("/accept/{contractId}")
    public ResponseEntity acceptContract(@AuthenticationPrincipal User proId,@PathVariable Integer contractId) {
        contractService.acceptContract(proId.getPro().getId(), contractId);
        return ResponseEntity.ok("Contract accepted.");
    }

    // Reject contrat
    @PutMapping("/reject/{contractId}")
    public ResponseEntity rejectContract(@AuthenticationPrincipal User proId,@PathVariable Integer contractId) {
        contractService.rejectContract(proId.getId(), contractId);
        return ResponseEntity.ok("Contract rejected.");
    }

    @PostMapping("/expireAccount")
    public ResponseEntity expireAccount(){
        proService.expireAccount();
        return ResponseEntity.status(200).body(new ApiResponse("Expired Pro accounts have been processed."));
    }

    @GetMapping("/{moderatorId}/{proId}/cv")
    public ResponseEntity<byte[]> downloadProCv(@PathVariable Integer proId,@PathVariable Integer moderatorId) {
        byte[] fileContent = proService.downloadProPDF(moderatorId,proId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);  // Set content type as PDF
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("Pro_CV_" + proId + ".pdf")
                .build());

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(fileContent);
    }

}