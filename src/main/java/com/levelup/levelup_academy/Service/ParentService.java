package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.ParentDTO;
import com.levelup.levelup_academy.Model.*;
import com.levelup.levelup_academy.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public List<Parent> getAllParents(Integer moderatorId) {
        Moderator moderator = moderatorRepository.findModeratorById(moderatorId);
        if(moderator == null) throw new ApiException("Moderator not found");
        return parentRepository.findAll();
    }

    public void registerParent(ParentDTO parentDTO) {
        parentDTO.setRole("PARENTS");
        String hashPassword = new BCryptPasswordEncoder().encode(parentDTO.getPassword());
        User user = new User(null, parentDTO.getUsername(), hashPassword, parentDTO.getEmail(), parentDTO.getFirstName(), parentDTO.getLastName(), parentDTO.getRole(), LocalDate.now(),null,null,null,null,null,null,null,null);

        Parent parent = new Parent(null, user,null, null);
        authRepository.save(user);
        parentRepository.save(parent);

//        User user = new User();
//        user.setRole("PARENTS");
//        user.setFirstName(parentDTO.getFirstName());
//        user.setLastName(parentDTO.getLastName());
//        user.setEmail(parentDTO.getEmail());
//        String hashPassword = new BCryptPasswordEncoder().encode(parentDTO.getPassword());
//        user.setPassword(hashPassword);
//
//        Parent parent = new Parent(null, user,null, null);
//        user.setParent(parent);
//        parentRepository.save(parent);
//        authRepository.save(user);
//

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
}
