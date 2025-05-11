package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.EmailRequest;
import com.levelup.levelup_academy.DTO.ModeratorDTO;
import com.levelup.levelup_academy.Model.Contract;
import com.levelup.levelup_academy.Model.Moderator;
import com.levelup.levelup_academy.Model.Pro;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Repository.ContractRepository;
import com.levelup.levelup_academy.Repository.ModeratorRepository;
import com.levelup.levelup_academy.Repository.ProRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static org.hibernate.annotations.UuidGenerator.Style.RANDOM;

@Service
@RequiredArgsConstructor
public class ModeratorService {
    private final ModeratorRepository moderatorRepository;
    private final AuthRepository authRepository;
    private final ContractRepository contractRepository;
    private final EmailNotificationService emailNotificationService;
    private final ProRepository proRepository;

    //GET
    public List<Moderator> getAllModerator(){
        return moderatorRepository.findAll();
    }

    //Register Moderator
    public void registerModerator(ModeratorDTO moderatorDTO){
        moderatorDTO.setRole("MODERATOR");
        User user = new User(null, moderatorDTO.getUsername(), moderatorDTO.getPassword(), moderatorDTO.getEmail(), moderatorDTO.getFirstName(), moderatorDTO.getLastName(), moderatorDTO.getRole(), LocalDate.now(),null,null,null,null,null,null,null,null);
        Moderator moderator = new Moderator(null,user);
        authRepository.save(user);
        moderatorRepository.save(moderator);
    }
    public void updateModerator(Integer id, ModeratorDTO moderatorDTO){
        Moderator moderator = moderatorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moderator not found"));

        User user = moderator.getUser();
        user.setUsername(moderatorDTO.getUsername());
        user.setPassword(moderatorDTO.getPassword());
        user.setEmail(moderatorDTO.getEmail());
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

    // Add the Pro ID as a PathVariable
    public void reviewContract(Integer contractId, Integer proId) {
        // Retrieve the contract
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ApiException("Contract not found"));

        // Retrieve the Pro using the provided Pro ID
        Pro pro = proRepository.findById(proId)
                .orElseThrow(() -> new ApiException("Pro not found"));

        // Check if the contract has already been reviewed
        if (contract.getModeratorStatus()) {
            throw new ApiException("Contract has already been reviewed");
        }

        // Mark the contract as reviewed
        contract.setModeratorStatus(true);
        contractRepository.save(contract);


            // Create email notification
            EmailRequest email = new EmailRequest();
            email.setRecipient(pro.getUser().getEmail());
            email.setSubject("Contract Reviewed");
            email.setMessage("Your contract has been reviewed by the moderator. Please check your dashboard for further actions.");

            // Send email to the selected Pro
            emailNotificationService.sendEmail(email);

    }



}
