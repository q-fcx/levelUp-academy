package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.ContractDTO;
import com.levelup.levelup_academy.DTO.EmailRequest;
import com.levelup.levelup_academy.DTO.ProDTO;
import com.levelup.levelup_academy.Model.Contract;
import com.levelup.levelup_academy.Model.Moderator;
import com.levelup.levelup_academy.Model.Pro;
import com.levelup.levelup_academy.Repository.ContractRepository;
import com.levelup.levelup_academy.Repository.ModeratorRepository;
import com.levelup.levelup_academy.Repository.ProRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public void addContract(Integer moderatorId, ContractDTO contractDTO) {

        Moderator moderator = moderatorRepository.findModeratorById(moderatorId);
              if(moderator == null) {
                  new ApiException("Moderator not found");
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
                ,null,null,null,null
        );
              contract.setModeratorStatus(false);

        contractRepository.save(contract);
        String message = "<html><body style='font-family: Arial, sans-serif; color: #fff; line-height: 1.6; background-color: #A53A10; padding: 40px 20px;'>" +
                "<div style='max-width: 600px; margin: auto; background: rgba(255, 255, 255, 0.05); border-radius: 12px; padding: 20px; text-align: center;'>" +
                "<img src='https://i.imgur.com/Q6FtCEu.jpeg' alt='LevelUp Academy Logo' style='width:90px; border-radius: 10px; margin-bottom: 20px;'/>" +
                "<h2 style='color: #fff;'>📄 New Contract Submitted</h2>" +
                "<p style='font-size: 16px;'>A new contract has been submitted with the following details:</p>" +
                "<table style='margin: 20px auto; color: #fff; font-size: 15px;'>" +
                "<tr><td style='padding: 5px 10px;'> Team:</td><td><b>" + contract.getTeam() + "</b></td></tr>" +
                "<tr><td style='padding: 5px 10px;'> Email:</td><td>" + contract.getEmail() + "</td></tr>" +
                "<tr><td style='padding: 5px 10px;'> Commercial Register:</td><td>" + contract.getCommercialRegister() + "</td></tr>" +
                "<tr><td style='padding: 5px 10px;'> Game:</td><td>" + contract.getGame() + "</td></tr>" +
                "<tr><td style='padding: 5px 10px;'> Start Date:</td><td>" + contract.getStartDate() + "</td></tr>" +
                "<tr><td style='padding: 5px 10px;'> End Date:</td><td>" + contract.getEndDate() + "</td></tr>" +
                "<tr><td style='padding: 5px 10px;'> Amount:</td><td>" + contract.getAmount() + " SAR</td></tr>" +
                "</table>" +
                "<p style='font-size: 14px;'>Please review this contract in your dashboard.</p>" +
                "<p style='font-size: 14px;'>– LevelUp Academy System</p>" +
                "</div></body></html>";

        contractRepository.save(contract);

        // Build email
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setRecipient(moderator.getUser().getEmail());
        emailRequest.setSubject("New Contract Added");
        emailRequest.setMessage(message);

        emailNotificationService.sendEmail(emailRequest);
    }

    // Accept contract by professional
    public void acceptContract(Integer proId,Integer contractId) {
        Pro pro = proRepository.findProById(proId);
        Contract contract = contractRepository.findContractById(contractId);
        if(contract == null){
             throw  new ApiException("Contract not found");
        }
        if(pro == null){
            throw new ApiException("The professional player is not found");
        }
        contract.setContractStatus("Accepted");
        List<Contract> otherContracts = contractRepository.findAllByPro(pro);
        for (Contract otherContract : otherContracts) {
            if (!otherContract.getId().equals(contractId)) {
                otherContract.setContractStatus("Rejected");
                contractRepository.save(otherContract);
            }
        }
        contractRepository.save(contract);
    }

    // reject the contract
    public void rejectContract(Integer proId,Integer contractId) {
        Pro pro = proRepository.findProById(proId);
        Contract contract = contractRepository.findContractById(contractId);
        if(contract == null) {
            throw new ApiException("Contract not found");
        }
        if(pro == null){
            throw new ApiException("The professional player is not found");
        }
        contract.setContractStatus("rejected");
        contractRepository.save(contract);
    }


}
