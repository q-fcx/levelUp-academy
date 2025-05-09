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
        if (authRepository.existsByEmail(proDTO.getEmail())) {
            throw new ApiException("Email is already in use");
        }

        if (authRepository.existsByUsername(proDTO.getUsername())) {
            throw new ApiException("Username is already in use");
        }
        String filePath = null;
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get("uploads/PDF/" + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());
                filePath = path.toString();
            } catch (IOException e) {
                throw new RuntimeException("Failed to save PDF file.");
            }
        }
        User user = new User(null,proDTO.getUsername(),proDTO.getPassword(),proDTO.getEmail(),proDTO.getFirstName(),proDTO.getLastName(),proDTO.getRole(),null,null,null,null,false);
        Pro pro = new Pro(null,filePath,user,null);
        authRepository.save(user);
        proRepository.save(pro);
    }


    public void edit(Integer proId,ProDTO proDTO){
        Pro pro = proRepository.findProById(proId);
        if(pro == null){
            throw new ApiException("The Professional Player you search for is not found ");
        }

        if(pro.getUser().getIsApproved().equals(false)){
            throw new ApiException("The Professional Player you search for is not approved yet ");
        }

        pro.getUser().setEmail(proDTO.getEmail());
        pro.getUser().setUsername(proDTO.getUsername());

        authRepository.save(pro.getUser());
        proRepository.save(pro);
    }

    public void delete(Integer proId){
        Pro pro = proRepository.findProById(proId);

        if (pro == null){
            throw new ApiException("The player is not found");
        }
        User user = pro.getUser();

        proRepository.delete(pro);
        authRepository.delete(user);
    }


// Admin approved the professional player account after checking the requirements
    public void approveProByAdmin(Integer adminId, Integer proId) {
        // to make sure it is an admin
        User admin = authRepository.findUserById(adminId);
        if(admin== null){
            throw new ApiException("Admin not found");
        }
        if (!admin.getRole().equals("ADMIN")) {
            throw new ApiException("Unauthorized: You must be an admin to approve players.");
        }
        Pro pro = proRepository.findProById(proId);
        if (pro == null) {
            throw new ApiException("The Professional Player you are looking for is not found.");
        }

        // to check the account not approved yet
        if (pro.getUser().getIsApproved()) {
            throw new ApiException("This player has already been approved.");
        }

        User user = pro.getUser();
        user.setIsApproved(true);
        pro.getUser().setIsApproved(true);
        authRepository.save(user);
        proRepository.save(pro);
    }

    // Admin can reject the professional account after checking on the requirements if not complete or enough , and delete the account
    public void rejectProByAdmin(Integer adminId, Integer proId) {
        User admin = authRepository.findUserById(adminId);
        if(admin== null){
            throw new ApiException("Admin not found");
        }
        if (!admin.getRole().equals("ADMIN")) {
            throw new ApiException("Unauthorized: You must be an admin to reject players.");
        }
        Pro pro = proRepository.findProById(proId);
        if (pro == null) {
            throw new ApiException("The Professional Player you are looking for is not found.");
        }

        User user = pro.getUser();
        user.setIsApproved(false);
        pro.getUser().setIsApproved(false);
        proRepository.delete(pro);
        authRepository.delete(user);
    }


}
