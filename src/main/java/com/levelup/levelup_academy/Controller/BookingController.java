package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.BookingRepository;
import com.levelup.levelup_academy.Service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/get-all")
    public ResponseEntity getMyBookings(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(bookingService.getMyBookings(user.getId()));
    }

    @PostMapping("add/{sessionId}/{subscriptionId}")
    public ResponseEntity addBooking(@AuthenticationPrincipal User user , @PathVariable Integer sessionId, @PathVariable Integer subscriptionId) {
        bookingService.addBooking(user.getId(),sessionId,subscriptionId);
        return ResponseEntity.status(200).body(new ApiResponse("Book added successfully"));
    }

    @PostMapping("check/{bookingId}")
    public ResponseEntity changeBookStatus(@AuthenticationPrincipal User user, @PathVariable Integer bookingId) {
        bookingService.checkBookState(user.getId(), bookingId);
        return ResponseEntity.status(200).body(new ApiResponse("Status Checked"));
    }

    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity cancelBooking(@AuthenticationPrincipal User user,@PathVariable Integer bookingId) {
        bookingService.cancelPendingBooking(user.getId(),bookingId);
        return ResponseEntity.ok(new ApiResponse("Booking cancelled successfully."));
    }

}
