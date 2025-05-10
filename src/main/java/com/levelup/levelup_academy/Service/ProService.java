package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.ProDTO;
import com.levelup.levelup_academy.Model.Pro;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Repository.ProRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProService {
    private final ProRepository proRepository;
    private final AuthRepository authRepository;

    //GET
    public List<Pro> getAllPro(){
        return proRepository.findAll();
    }

    //Register pro player

    public void registerPro(ProDTO proDTO, MultipartFile file){
        proDTO.setRole("PRO");
        String filePath = null;
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get("uploads/cvs/" + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());
                filePath = path.toString();
            } catch (IOException e) {
                throw new RuntimeException("Failed to save CV file.");
            }
        }
        User user = new User(null,proDTO.getUsername(),proDTO.getPassword(),proDTO.getEmail(),proDTO.getFirstName(),proDTO.getLastName(),proDTO.getRole(),false,null,null,null,null,null,null,null);
        Pro pro = new Pro(null,filePath, user,null,null);
        authRepository.save(user);
        proRepository.save(pro);
    }


    public void edit(Integer proId,ProDTO proDTO){
        Pro pro = proRepository.findProById(proId);
        if(pro == null){
            throw new ApiException("The Professional Player you search for is not found ");
        }

        if (pro.getUser().getIaApproved() == false) {
            throw new ApiException("The Professional Player you search for is not approved yet ");
        }
        if (authRepository.existsByEmail(proDTO.getUsername()) && !pro.getUser().getEmail().equals(proDTO.getEmail())) {
            throw new ApiException("Email is already in use");

        }

        if (authRepository.existsByUsername(proDTO.getUsername()) && !pro.getUser().getUsername().equals(proDTO.getUsername())) {
            throw new ApiException("Username is already in use");
        }
        pro.getUser().setEmail(proDTO.getEmail());
        pro.getUser().setUsername(proDTO.getUsername());

        authRepository.save(pro.getUser());
        proRepository.save(pro);
    }

    public void delete(Integer proId) {
        Pro pro = proRepository.findProById(proId);

        if (pro == null) {
            throw new ApiException("The player is not found");
        }
        User user = pro.getUser();

        proRepository.delete(pro);
        authRepository.delete(user);
    }


    public byte[] downloadProCv(Integer proId) {
        Pro pro = proRepository.findById(proId)
                .orElseThrow(() -> new RuntimeException("Pro not found"));

        String filePath = pro.getCvPath();
        if (filePath == null || filePath.isEmpty()) {
            throw new RuntimeException("CV not uploaded for this pro.");
        }

        try {
            Path path = Paths.get(filePath).toAbsolutePath().normalize();
            if (!Files.exists(path) || !Files.isReadable(path)) {
                throw new RuntimeException("CV file not found or not readable.");
            }

            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CV file", e);
        }
    }

    // Approving professional player request by admin if the pdf match the requirement
    public void approveProByAdmin(Integer adminId, Integer proId) {
        User admin = authRepository.findUserById(adminId);
        if (!admin.getRole().equals("ADMIN")) {
            throw new ApiException("Unauthorized: You must be an admin to approve players.");
        }
        Pro pro = proRepository.findProById(proId);
        if (pro == null) {
            throw new ApiException("The Professional Player you are looking for is not found.");
        }
        if (pro.getUser().getIaApproved()) {
            throw new ApiException("This player has already been approved.");
        }
        User user = pro.getUser();
        user.setIaApproved(true);
        pro.getUser().setIaApproved(true);
        authRepository.save(user);
        proRepository.save(pro);
    }

    // rejecting the professional player request by admin if the pdf not match the requirement
    public void rejectProByAdmin(Integer adminId, Integer proId) {
        User admin = authRepository.findUserById(adminId);
        if (!admin.getRole().equals("ADMIN")) {
            throw new ApiException("Unauthorized: You must be an admin to reject players.");
        }
        Pro pro = proRepository.findProById(proId);
        if (pro == null) {
            throw new ApiException("The Professional Player you are looking for is not found.");
        }

        User user = pro.getUser();
        user.setIaApproved(false);
        pro.getUser().setIaApproved(false);
        proRepository.delete(pro);
        authRepository.delete(user);
    }
}