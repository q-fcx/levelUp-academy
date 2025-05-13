package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.EmailRequest;
import com.levelup.levelup_academy.DTO.ParentDTO;
import com.levelup.levelup_academy.DTOOut.ChildDTOOut;
import com.levelup.levelup_academy.DTOOut.ParentDTOOut;
import com.levelup.levelup_academy.Model.*;
import com.levelup.levelup_academy.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final ParentRepository parentRepository;
    private final AuthRepository authRepository;
    private final ChildRepository childRepository;
    private final GameRepository gameRepository;
    private final StatisticChildRepository statisticChildRepository;
    private final ModeratorRepository moderatorRepository;
    private final EmailNotificationService emailNotificationService;

    public List<ParentDTOOut> getAllParents(Integer moderatorId) {
        Moderator moderator = moderatorRepository.findModeratorById(moderatorId);
        if(moderator == null) throw new ApiException("Moderator not found");
        List<Parent> parents = parentRepository.findAll();
        List<ParentDTOOut> parentDTOOuts = new ArrayList<>();

        for (Parent parent : parents){
            User parentUser = parent.getUser();
            List<ChildDTOOut> childDTOOuts = new ArrayList<>();
            for (Child child : parent.getChildren()){
                User childUser = child.getParent().getUser();
                StatisticChild statistics = child.getStatistics();
                childDTOOuts.add(new ChildDTOOut(childUser.getUsername(), childUser.getFirstName(), childUser.getLastName(),childUser.getEmail(),statistics));

            }
            parentDTOOuts.add(new ParentDTOOut(parentUser.getUsername(),parentUser.getFirstName(),parentUser.getLastName(),parentUser.getEmail(),childDTOOuts));
        }
        return parentDTOOuts;
    }

    public void registerParent(ParentDTO parentDTO) {
        parentDTO.setRole("PARENTS");
        String hashPassword = new BCryptPasswordEncoder().encode(parentDTO.getPassword());
        User user = new User(null, parentDTO.getUsername(), hashPassword, parentDTO.getEmail(), parentDTO.getFirstName(), parentDTO.getLastName(), parentDTO.getRole(), LocalDate.now(),null,null,null,null,null,null,null,null);

        Parent parent = new Parent(null, user,null, null);
        authRepository.save(user);
        parentRepository.save(parent);

        String subject = "Welcome to LevelUp Academy ";
        String message = "<html><body style='font-family: Arial, sans-serif; color: #fff; line-height: 1.6; background-color: #A53A10; padding: 40px 20px;'>" +
                "<div style='max-width: 600px; margin: auto; background: rgba(255, 255, 255, 0.05); border-radius: 12px; padding: 20px; text-align: center;'>" +
                "<img src='https://i.imgur.com/Q6FtCEu.jpeg' alt='LevelUp Academy Logo' style='width:90px; border-radius: 10px; margin-bottom: 20px;'/>" +
                "<h2 style='color: #fff;'>👨‍👩‍👧 Welcome to <span style='color: #FFD700;'>LevelUp Academy</span>, " + parentDTO.getFirstName() + "!</h2>" +
                "<p style='font-size: 16px;'>We're excited to have you as part of our growing community of supportive parents.</p>" +
                "<p style='font-size: 16px;'> Please don’t forget to <b>register your child</b> so they can begin their learning journey with us.</p>" +
                "<p style='font-size: 16px;'> If you need any help, feel free to contact our support team anytime.</p>" +
                "<p style='font-size: 15px;'>With warm regards,<br/><b>The LevelUp Academy Team</b></p>" +
                "</div>" +
                "</body></html>";

        EmailRequest emailRequest = new EmailRequest(parentDTO.getEmail(),message, subject);
        emailNotificationService.sendEmail(emailRequest);

    }



    public void editParent(Integer parentId, ParentDTO parentDTO) {
        Parent parent = parentRepository.findParentById(parentId);
        if (parent == null) {
            throw new ApiException("Parent not found");
        }
        User user = parent.getUser();
        user.setUsername(parentDTO.getUsername());
        user.setEmail(parentDTO.getEmail());
        user.setFirstName(parentDTO.getFirstName());
        user.setLastName(parentDTO.getLastName());
        String hashPassword = new BCryptPasswordEncoder().encode(parentDTO.getPassword());
        user.setPassword(hashPassword);

        authRepository.save(user);
        parentRepository.save(parent);
    }

    public void deleteParent(Integer parentId) {
        Parent parent = parentRepository.findParentById(parentId);
        if (parent == null) {
            throw new ApiException("Parent not found");
        }
        User user = parent.getUser();
        parentRepository.delete(parent);
        authRepository.delete(user);
    }

    public void addChildToParent(Integer parentId, Child child) {
        Parent parent = parentRepository.findParentById(parentId);
        if(parent == null) throw new ApiException("Parent not found");

        child.setParent(parent);
        childRepository.save(child);
    }

    public void updateChild(Integer parentId, Integer childId,Child child) {
        Parent parent = parentRepository.findParentById(parentId);
        if(parent == null) throw new ApiException("Parent not found");

        Child ch = childRepository.findChildById(childId);
        if(child == null) throw new ApiException("Child not found");

        if (!child.getParent().getId().equals(parentId)) {
            throw new ApiException("Child does not belong to the parent");
        }
        ch.setFirstName(child.getFirstName());
        ch.setLastName(child.getLastName());
        ch.setAge(child.getAge());

        childRepository.save(ch);
    }

    public void deleteChild(Integer parentId, Integer childId) {
        Parent parent = parentRepository.findParentById(parentId);
        if (parent == null) throw new ApiException("Parent not found");


        Child child = childRepository.findChildById(childId);
        if (child == null) throw new ApiException("Child not found");

        if (!child.getParent().getId().equals(parentId)) {
            throw new ApiException("Child does not belong to the parent");
        }

        childRepository.delete(child);
    }

    public StatisticChild getChildStatistic(Integer parentId,Integer childId) {
        Child child = childRepository.findChildById(childId);
        if (child == null) throw new ApiException("Child not found");

        Parent parent = parentRepository.findParentById(parentId);
        if(parent == null) throw new ApiException("Parent not found");

        StatisticChild statistic = child.getStatistics();
        if (statistic == null) throw new ApiException("Statistics not found for this child");

        return statistic;
    }

    public List<Game> getGamesByChildAge(Integer parentId, Integer childId) {
        Parent parent = parentRepository.findParentById(parentId);
        if(parent == null) throw new ApiException("Parent not found");

        Child child = childRepository.findChildById(childId);
        if(child == null) throw new ApiException("Child not found");

        return gameRepository.findGamesByAgeIsLessThanEqual(child.getAge());
    }

    public StatisticChild getMyChildStatisticsByChildId(Integer parentId,Integer childId) {
        Parent parent = parentRepository.findParentById(parentId);
        if (parent == null){
            throw new ApiException("Parent not found");
        }
        StatisticChild stat = statisticChildRepository.findByChild_Id(childId);
        if (stat == null) throw new ApiException("Statistic not found for this child");
        return stat;
    }
}
