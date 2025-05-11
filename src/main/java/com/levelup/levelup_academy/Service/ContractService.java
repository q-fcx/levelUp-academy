package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.ContractDTO;
import com.levelup.levelup_academy.DTO.EmailRequest;
import com.levelup.levelup_academy.Model.Contract;
import com.levelup.levelup_academy.Model.Moderator;
import com.levelup.levelup_academy.Model.Pro;
import com.levelup.levelup_academy.Repository.ContractRepository;
import com.levelup.levelup_academy.Repository.ModeratorRepository;
import com.levelup.levelup_academy.Repository.ProRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final EmailNotificationService emailNotificationService;
    private final ProRepository proRepository;
    private final ModeratorRepository moderatorRepository;

    //GET
    public List<Contract> getAllContract(Integer proId) {
        Pro pro = proRepository.findProById(proId);
        if(pro== null){
            throw new ApiException("The pro player is not found");
        }
        return contractRepository.findAll();
    }

    //ADD
    public void addContract(ContractDTO contractDTO, Integer moderatorId) {

        // Proceed with creating and saving the contract
        Moderator moderator = moderatorRepository.findModeratorById(moderatorId);
              if(moderator == null) {new ApiException("Moderator not found with ID: " + contractDTO.getModeratorId());
              }
        Contract contract = new Contract(
                null,
                contractDTO.getTeam(),
                contractDTO.getEmail(),
                contractDTO.getCommercialRegister(),
                contractDTO.getGame(),
                contractDTO.getStartDate(),
                contractDTO.getEndDate(),
                contractDTO.getAmount(),false
                ,false,null,null
                // Pro is still null
        );
              contract.setModeratorStatus(false);

        contractRepository.save(contract);

        // Save contract first to generate the ID
        contractRepository.save(contract);

        // Build email
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setRecipient(pro.getUser().getEmail());
        emailRequest.setSubject("Contract Confirmation");
        emailRequest.setMessage("Dear " + contract.getTeam() + ",\n\n" +
                "Your contract has been successfully added. Details:\n" +
                "- Game: " + contract.getGame() + "\n" +
                "- Start Date: " + contract.getStartDate() + "\n" +
                "- End Date: " + contract.getEndDate() + "\n" +
                "- Amount: " + contract.getAmount() + "\n\n" +
                "Regards,\nReal Estate Team");

        // Send email
        emailNotificationService.sendEmail(emailRequest);

    }
}
