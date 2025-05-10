package com.levelup.levelup_academy.Controller;


import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity registerAdmin(User user){
      userService.register(user);
       return ResponseEntity.status(200).body("Adding admin successfully");
    }



}
