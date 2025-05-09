package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.DTO.ProDTO;
import com.levelup.levelup_academy.DTO.TrainerDTO;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Service.ProService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/pro")
@RequiredArgsConstructor
public class ProController {
    private final ProService proService;

    //GET
    @GetMapping("/get")
    public ResponseEntity getAllPro() {
        return ResponseEntity.status(200).body(proService.getAllPro());
    }

    //Register pro player
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registerTrainer(
            @RequestPart("pro") ProDTO proDTO,
            @RequestPart("file") MultipartFile file) {
        proService.registerPro(proDTO, file);
        return ResponseEntity.ok("pro player registered successfully with CV uploaded");
    }

//    //Edit
//    @PutMapping("/edit")
//    public ResponseEntity EditProAccount( @RequestBody @Valid ProDTO proDTO) {
//        proService.edit(pro.getId(), proDTO);
//        return ResponseEntity.ok("Pro player information updated successfully");
//    }
//
//    // Endpoint to delete Pro player by ID
//    @DeleteMapping("/delete")
//    public ResponseEntity<String> deleteProPlayer(@AuthenticationPrincipal User pro) {
//        proService.delete(pro.getId());
//        return ResponseEntity.ok("Your account have been deleted successfully.");
//
//    }

    @GetMapping("/{id}/cv")
    public ResponseEntity<byte[]> downloadProCv(@PathVariable Integer id) {
        byte[] cvContent = proService.downloadProCv(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition
                .builder("attachment")
                .filename("pro_cv_" + id + ".pdf")
                .build());

        return new ResponseEntity<>(cvContent, headers, HttpStatus.OK);
    }
}