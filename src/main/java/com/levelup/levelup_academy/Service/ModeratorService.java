package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.EmailRequest;
import com.levelup.levelup_academy.DTO.ModeratorDTO;
import com.levelup.levelup_academy.Model.*;
import com.levelup.levelup_academy.Repository.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModeratorService {
    private final ModeratorRepository moderatorRepository;
    private final AuthRepository authRepository;
    private final ContractRepository contractRepository;
    private final EmailNotificationService emailNotificationService;
    private final ProRepository proRepository;
    private final ParentRepository parentRepository;
    private final UltraMsgService ultraMsgService;

    //GET
    public List<Moderator> getAllModerator(Integer adminId){
        User admin = authRepository.findUserById(adminId);
        if(admin == null) throw new ApiException("Admin not found");
        return moderatorRepository.findAll();
    }

    public void registerModerator(Integer adminId, ModeratorDTO moderatorDTO){
        User admin = authRepository.findUserById(adminId);
        String hashPassword = new BCryptPasswordEncoder().encode(moderatorDTO.getPassword());
        if(admin == null) throw new ApiException("Admin not found");


        moderatorDTO.setRole("MODERATOR");


        User user = new User();
        user.setUsername(moderatorDTO.getUsername());
        user.setPassword(hashPassword);
        user.setEmail(moderatorDTO.getEmail());
        user.setFirstName(moderatorDTO.getFirstName());
        user.setLastName(moderatorDTO.getLastName());
        user.setRole("MODERATOR");


        Moderator moderator = new Moderator();
        moderator.setUser(user);
        user.setModerator(moderator);


        authRepository.save(user);


    }

    public void updateModerator(Integer id, ModeratorDTO moderatorDTO){
        Moderator moderator = moderatorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moderator not found"));

        User user = moderator.getUser();
        user.setUsername(moderatorDTO.getUsername());
        user.setPassword(moderatorDTO.getPassword());
        user.setEmail(moderatorDTO.getEmail());
        String hashPassword = new BCryptPasswordEncoder().encode(moderatorDTO.getPassword());
        user.setPassword(hashPassword);
        user.setFirstName(moderatorDTO.getFirstName());
        user.setLastName(moderatorDTO.getLastName());

        authRepository.save(user);
    }
    public void deleteModerator(Integer id){
        Moderator moderator = moderatorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moderator not found"));

        authRepository.delete(moderator.getUser());
        moderatorRepository.delete(moderator);
    }

    //


    public void reviewContract(Integer moderatorId, Integer contractId, Integer proId) {
        Moderator moderator = moderatorRepository.findModeratorById(moderatorId);
        if(moderator == null) throw new ApiException("Moderator not found");

        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ApiException("Contract not found"));


        Pro pro = proRepository.findById(proId)
                .orElseThrow(() -> new ApiException("Pro not found"));

        if (contract.getModeratorStatus()) {
            throw new ApiException("Contract has already been reviewed");
        }

        contract.setModeratorStatus(true);
        contractRepository.save(contract);

        String message = "<html><body style='font-family: Arial, sans-serif; color: #fff; background-color: #A53A10; padding: 40px 20px;'>" +
                "<div style='max-width: 600px; margin: auto; background: rgba(255, 255, 255, 0.05); border-radius: 12px; padding: 20px; text-align: center;'>" +
                "<img src='https://i.imgur.com/Q6FtCEu.jpeg' alt='LevelUp Academy Logo' style='width:90px; border-radius: 10px; margin-bottom: 20px;'/>" +
                "<h2>Contract Review Notice</h2>" +
                "<p style='font-size: 16px;'>Dear <b>" + pro.getUser().getFirstName() + "</b>,</p>" +
                "<p style='font-size: 16px;'>Your contract has been reviewed by our moderators and has been conditionally approved.</p>" +
                "<p style='font-size: 16px; text-align: left; padding: 10px 0;'><b>Conditions:</b></p>" +
                "<ul style='font-size: 15px; text-align: left; line-height: 1.6; padding-left: 20px;'>" +
                "<li>A <b>10%</b> service fee will be deducted from your contract amount.</li>" +
                "<li>Your account will be <b>temporarily paused</b> until you accept these conditions.</li>" +
                "<li>You must maintain a <b>minimum rating of 4.5</b>.</li>" +
                "<li>At least <b>5 sessions/month</b> are required to remain eligible for bonuses.</li>" +
                "<li><b>Violations</b> of academy guidelines may result in termination.</li>" +
                "<li>Payment will be processed <b>monthly</b> after session report approval.</li>" +
                "<li>Monthly feedback meetings with moderators are <b>mandatory</b>.</li>" +
                "</ul>" +
                "<p style='font-size: 16px;'>Please log in to your dashboard to confirm or decline the contract.</p>" +
                "<p style='font-size: 14px;'>– The LevelUp Academy Team</p>" +
                "</div></body></html>";
            EmailRequest email = new EmailRequest();
            email.setRecipient(pro.getUser().getEmail());
            email.setSubject("Contract Reviewed");
            email.setMessage(message);

            emailNotificationService.sendEmail(email);

    }

    public void sendReportToParent(Integer parentId, String reportContent) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new ApiException("Parent not found"));

        String phoneNumber = parent.getPhoneNumber();

        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new ApiException("Parent phone number is missing");
        }

        String message = ":clipboard: *Child Progress Report*\n\n" +
                reportContent + "\n\n" +
                "Thank you for being part of LevelUp Academy!\n– The Moderator Team";

        ultraMsgService.sendWhatsAppMessage(phoneNumber, message);
    }


}
