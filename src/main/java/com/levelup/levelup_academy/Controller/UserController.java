package com.levelup.levelup_academy.Controller;


import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("generate/{adminId}/{moderatorId}")
    public ResponseEntity generateModeratorLogin(@PathVariable Integer adminId, @PathVariable Integer moderatorId){
        userService.generateModeratorLogin(adminId, moderatorId);
        return ResponseEntity.status(200).body(new ApiResponse("Username and Password generated for moderator"));
    }



}
