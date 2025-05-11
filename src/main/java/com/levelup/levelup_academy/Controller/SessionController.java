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
    @PostMapping("/add/{moderatorId}/{trainerId}/{gameId}")
    public ResponseEntity addSession(@RequestBody @Valid Session session, @PathVariable Integer moderatorId,@PathVariable Integer trainerId,@PathVariable Integer gameId){
        sessionService.addClass(session,moderatorId,trainerId,gameId);
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

    //update
    @PutMapping("/update/{moderatorId}/{sessionId}")
    public ResponseEntity updateSession(@PathVariable Integer moderatorId ,@PathVariable Integer sessionId, @RequestBody @Valid Session session){
        sessionService.updateSession(moderatorId,session,sessionId);
        return ResponseEntity.status(200).body(new ApiResponse("Session Updated"));
    }

    //delete
    @DeleteMapping("/del/{moderatorId}/{sessionId}")
    public ResponseEntity deleteSession(@PathVariable Integer moderatorId,@PathVariable Integer sessionId){
        sessionService.deleteSession(moderatorId, sessionId);
        return ResponseEntity.status(200).body(new ApiResponse("Session Deleted"));
    }


    @GetMapping("/get-players/{sessionId}")
    public ResponseEntity getAllPlayersInSession(@PathVariable Integer sessionId) {
        return ResponseEntity.status(200).body(sessionService.getAllPlayersInSession(sessionId));
    }



}
