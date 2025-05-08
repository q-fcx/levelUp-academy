package com.levelup.levelup_academy.Service;

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
        User user = new User(null,proDTO.getUsername(),proDTO.getPassword(),proDTO.getEmail(),proDTO.getFirstName(),proDTO.getLastName(),proDTO.getRole(),null,null,null,null);
        Pro pro = new Pro(null,filePath,user);
        authRepository.save(user);
        proRepository.save(pro);
    }
}
