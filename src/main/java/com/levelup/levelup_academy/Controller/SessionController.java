package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.Model.Session;
import com.levelup.levelup_academy.Service.SessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/session")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    //GET
    @GetMapping("/get")
    public ResponseEntity getAllSession(){
        return ResponseEntity.status(200).body(sessionService.getAllClasses());
    }

    //ADD
    @PostMapping("/add/{moderatorId}")
    public ResponseEntity addSession(@RequestBody @Valid Session session, @PathVariable Integer moderatorId){
        sessionService.addClass(session,moderatorId);
        return ResponseEntity.status(200).body(new ApiResponse("Session Added"));
    }

    //Assign
    @PostMapping("assignTrainer/{trainerId}/{sessionId}")
    public ResponseEntity assignTrainerToSession(@PathVariable Integer trainerId,@PathVariable Integer sessionId){
        sessionService.assignTrainerToSession(trainerId, sessionId);
        return ResponseEntity.status(200).body(new ApiResponse("Assigned"));
    }

    //Assign
    @PostMapping("assignGame/{sessionId}")
    public ResponseEntity assignGameToSession(@PathVariable Integer sessionId){
        sessionService.assignGameToTrainer(sessionId);
        return ResponseEntity.status(200).body(new ApiResponse("Assigned"));
    }



}
