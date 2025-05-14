package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.Model.Session;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Service.SessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PostMapping("/add/{trainerId}/{gameId}")
    public ResponseEntity addSession(@AuthenticationPrincipal User moderator, @RequestBody @Valid Session session, @PathVariable Integer trainerId, @PathVariable Integer gameId){
        sessionService.addClass(moderator.getModerator().getId(),session,trainerId,gameId);
        return ResponseEntity.status(200).body(new ApiResponse("Session Added"));
    }

    //Assign
//    @PostMapping("assignTrainer/{trainerId}/{sessionId}")
//    public ResponseEntity assignTrainerToSession(@PathVariable Integer trainerId,@PathVariable Integer sessionId){
//        sessionService.assignTrainerToSession(trainerId, sessionId);
//        return ResponseEntity.status(200).body(new ApiResponse("Assigned"));
//    }

    //Assign
//    @PostMapping("assignGame/{sessionId}")
//    public ResponseEntity assignGameToSession(@PathVariable Integer sessionId){
//        sessionService.assignGameToTrainer(sessionId);
//        return ResponseEntity.status(200).body(new ApiResponse("Assigned"));
//    }

    //update
    @PutMapping("/update/{sessionId}")
    public ResponseEntity updateSession(@AuthenticationPrincipal User moderator ,@PathVariable Integer sessionId, @RequestBody @Valid Session session){
        sessionService.updateSession(moderator.getId(),session,sessionId);
        return ResponseEntity.status(200).body(new ApiResponse("Session Updated"));
    }

    //delete
    @DeleteMapping("/del/{sessionId}")
    public ResponseEntity deleteSession(@AuthenticationPrincipal User moderator,@PathVariable Integer sessionId){
        sessionService.deleteSession(moderator.getId(), sessionId);
        return ResponseEntity.status(200).body(new ApiResponse("Session Deleted"));
    }

    @GetMapping("/notify-start/{sessionId}")
    public ResponseEntity notifyUsersOfSessionStart(@AuthenticationPrincipal User trainer, @PathVariable Integer sessionId) {
        sessionService.notifyUsersIfSessionStarting(trainer.getId(),sessionId);
        return ResponseEntity.ok(new ApiResponse("Emails sent to all booked users for session ID: " + sessionId));
    }

    @PutMapping("/change-session/{trainerId}/{newSessionId}")
    public ResponseEntity changeTrainerSession(@AuthenticationPrincipal User moderator,
            @PathVariable Integer trainerId,
            @PathVariable Integer newSessionId) {
        sessionService.changeTrainerSession(moderator.getId(), trainerId, newSessionId);
        return ResponseEntity.ok(new ApiResponse("Trainer session changed successfully."));
    }



}
