package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.EmailRequest;
import com.levelup.levelup_academy.DTO.ProDTO;
import com.levelup.levelup_academy.Model.*;
import com.levelup.levelup_academy.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProService {
    private final ProRepository proRepository;
    private final AuthRepository authRepository;
    private final EmailNotificationService emailNotificationService;
    private final ModeratorRepository moderatorRepository;
    private final SessionRepository sessionRepository;
    private final ContractRepository contractRepository;

    //GET All Pro players by moderator
    public List<Pro> getAllPro(Integer moderatorId) {
        Moderator moderator= moderatorRepository.findModeratorById(moderatorId);
        if(moderator == null){
            throw new ApiException("Moderator not found");
        }
        return proRepository.findAll();
    }

    public Pro getPro(Integer moderatorId,Integer proId){
        Moderator moderator= moderatorRepository.findModeratorById(moderatorId);
        if(moderator == null){
            throw new ApiException("Moderator not found");
        }
        Pro pro = proRepository.findProById(proId);
        if(pro == null){
            throw new ApiException("Pro player is not found");
        }
        return pro;
    }


    //Register pro player

    public void registerPro(ProDTO proDTO, MultipartFile file) {
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
        String hashPassword = new BCryptPasswordEncoder().encode(proDTO.getPassword());
        User user = new User(null, proDTO.getUsername(), hashPassword, proDTO.getEmail(), proDTO.getFirstName(), proDTO.getLastName(), proDTO.getRole(), LocalDate.now(),null,null,null,null,null,null,null,null);
        Pro pro = new Pro(null, filePath, user, null, null,null,false);
        authRepository.save(user);
        proRepository.save(pro);
    }


    public void edit(Integer proId, ProDTO proDTO) {
        Pro pro = proRepository.findProById(proId);
        if (pro == null) {
            throw new ApiException("The Professional Player you search for is not found ");
        }

        if (pro.getIsApproved().equals(false)) {
            throw new ApiException("The Professional Player you search for is not approved yet ");
        }
        if (authRepository.existsByEmail(proDTO.getUsername()) && !pro.getUser().getEmail().equals(proDTO.getEmail())) {
            throw new ApiException("Email is already in use");

        }

        if (authRepository.existsByUsername(proDTO.getUsername()) && !pro.getUser().getUsername().equals(proDTO.getUsername())) {
            throw new ApiException("Username is already in use");
        }
        String hashPassword = new BCryptPasswordEncoder().encode(proDTO.getPassword());
        pro.getUser().setPassword(hashPassword);
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
        if (pro.getIsApproved().equals(false)) {
            throw new ApiException("The Professional Player is not approved yet ");
        }
        proRepository.delete(pro);
        authRepository.delete(user);
    }


    public byte[] downloadProPDF(Integer moderateId,Integer proId) {
        Moderator moderator = moderatorRepository.findModeratorById(moderateId);
        if(moderator == null){
            throw new ApiException("Moderator not found");
        }
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
        if (pro.getIsApproved()) {
            throw new ApiException("This player has already been approved.");
        }
        User user = pro.getUser();
        pro.setIsApproved(true);
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
        pro.setIsApproved(false);
        proRepository.delete(pro);
        authRepository.delete(user);
    }

//moderator can see all the requests
    public List<Pro> getAllProRequests(Integer moderatorId){
        Moderator moderator = moderatorRepository.findModeratorById(moderatorId);
        if(moderator == null){
            throw new ApiException("Moderator not found");
        }
        return proRepository.findByIsApproved(false);
    }

    public void sendDiscordExamLink(Integer moderatorId,Integer proId) {
        Pro pro = proRepository.findProById(proId);
        Moderator moderator = moderatorRepository.findModeratorById(moderatorId);

        if (pro == null) {
            throw new ApiException("The Professional Player you are looking for is not found.");
        }
        if (moderator == null) {
            throw new ApiException("Moderator is not found.");
        }
        String discordChannelLink = "https://discord.com/invite/yourChannelID";

        String emailMessage = "Dear " + pro.getUser().getUsername() + ",\n\n" +
                "Please click the link below to join the Discord channel and demonstrate your professional skills:\n\n" +
                discordChannelLink + "\n\n" +
                "Best regards,\n" +
                "The Team";

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setRecipient(pro.getUser().getEmail());
        emailRequest.setSubject("Discord Exam Link");
        emailRequest.setMessage(emailMessage);

        emailNotificationService.sendEmail(emailRequest);
    }




    public void expireAccount() {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);

        List<Contract> contracts = contractRepository.findByStartDateBefore(oneYearAgo);

        for (Contract contract : contracts) {
            if (contract.getStartDate().isBefore(oneYearAgo) && contract.getContractStatus().equals("Accepted")) {
                Pro pro = contract.getPro();
                sendAccountDeletionEmail(pro);

                contractRepository.save(contract);  // Save the updated contract
            }
        }
    }
    // Method to send email to pro player about account deleting the account
    private void sendAccountDeletionEmail(Pro pro) {
        String subject = "Your Account Has Been Deleted Due to Contract Expiry";
        String body = String.format("Dear %s,\n\nYour contract has expired, and your account has been deleted as a result. If you have any questions, please contact support.\n\nBest regards,\nTeam",pro.getUser().getEmail());

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setRecipient(pro.getUser().getEmail());
        emailRequest.setSubject(subject);
        emailRequest.setMessage(body);

        emailNotificationService.sendEmail(emailRequest);
    }

}