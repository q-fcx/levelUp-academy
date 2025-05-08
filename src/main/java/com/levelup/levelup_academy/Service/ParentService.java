package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.DTO.ParentDTO;
import com.levelup.levelup_academy.Model.Parent;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final ParentRepository parentRepository;
    private final AuthRepository authRepository;

    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    public void registerParent(ParentDTO parentDTO) {
        parentDTO.setRole("PARENTS");
        User user = new User(null, parentDTO.getUsername(), parentDTO.getPassword(), parentDTO.getEmail(), parentDTO.getFirstName(), parentDTO.getLastName(), parentDTO.getRole(), null, null, null,null,false);

        Parent parent = new Parent(null, user,null, null);
        authRepository.save(user);
        parentRepository.save(parent);

    }


}
