package com.levelup.levelup_academy.Controller;

import com.levelup.levelup_academy.Api.ApiResponse;
import com.levelup.levelup_academy.Repository.BookingRepository;
import com.levelup.levelup_academy.Service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/get-all")
    public ResponseEntity getAllBookings() {
        return ResponseEntity.status(200).body(bookingService.getAllBookings());
    }

    @PostMapping("add/{userId}/{sessionId}/{subscriptionId}")
    public ResponseEntity addBooking(@PathVariable Integer userId, @PathVariable Integer sessionId, @PathVariable Integer subscriptionId) {
        bookingService.addBooking(userId,sessionId,subscriptionId);
        return ResponseEntity.status(200).body(new ApiResponse("Book added successfully"));
    }

    @PostMapping("check/{bookingId}")
    public ResponseEntity changeBookStatus(@PathVariable Integer bookingId) {
        bookingService.checkBookState(bookingId);
        return ResponseEntity.status(200).body(new ApiResponse("Status Checked"));
    }

    @PutMapping("/cancel/{bookingId}/{userId}")
    public ResponseEntity<String> cancelBooking(@PathVariable Integer userId,@PathVariable Integer bookingId) {
        bookingService.cancelPendingBooking(userId,bookingId);
        return ResponseEntity.ok("Booking cancelled successfully.");
    }

}
