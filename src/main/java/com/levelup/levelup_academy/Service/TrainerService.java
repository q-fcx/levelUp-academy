package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.DTO.TrainerDTO;
import com.levelup.levelup_academy.Model.Trainer;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Repository.TrainerRepository;
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
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final AuthRepository authRepository;

    //GET
    public List<Trainer> getAllTrainers(){
        return trainerRepository.findAll();
    }
    //Register Trainer
    public void registerTrainer(TrainerDTO trainerDTO, MultipartFile file){
        trainerDTO.setRole("TRAINER");
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
        User user = new User(null,trainerDTO.getUsername(),trainerDTO.getPassword(),trainerDTO.getEmail(),trainerDTO.getFirstName(),trainerDTO.getLastName(),trainerDTO.getRole(),null,null,null,null,false);
        Trainer trainer = new Trainer(null,filePath,trainerDTO.getIsAvailable(),null, user, null);
        authRepository.save(user);
        trainerRepository.save(trainer);
    }

    //download
    public byte[] downloadTrainerCv(Integer trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        String filePath = trainer.getCvPath();
        if (filePath == null || filePath.isEmpty()) {
            throw new RuntimeException("CV not uploaded for this trainer.");
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

//    public void update

}
