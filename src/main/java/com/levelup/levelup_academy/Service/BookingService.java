package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.Model.Booking;
import com.levelup.levelup_academy.Model.Session;
import com.levelup.levelup_academy.Model.Subscription;
import com.levelup.levelup_academy.Model.User;
import com.levelup.levelup_academy.Repository.AuthRepository;
import com.levelup.levelup_academy.Repository.BookingRepository;
import com.levelup.levelup_academy.Repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SessionRepository sessionRepository;
    private final AuthRepository authRepository;


    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public void addBooking(Integer userId, Integer sessionId){
        User user = authRepository.findUserById(userId);
        if(user == null) throw new ApiException("User not found");

        Session session = sessionRepository.findSessionById(sessionId);
        if(session == null) throw new ApiException("Session not found");

        Booking booking = new Booking();
        booking.setBookDate(LocalDateTime.now());
        booking.setUser(user);
        booking.setSession(session);

        authRepository.save(user);
        sessionRepository.save(session);
        bookingRepository.save(booking);

    }

    public void checkBookState(Integer bookingId) {
        Booking booking = bookingRepository.findBookingById(bookingId);
        if(booking == null) throw new ApiException("Booking not found");
        Session session = sessionRepository.findSessionById(booking.getSession().getId());

        if(booking.getBookDate().isEqual(session.getStartDate())) {
            booking.setStatus("ACTIVE");
        }

    }
    public void cancelPendingBooking(Integer userId, Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ApiException("Booking not found"));

        User user = booking.getUser();

        if (!user.getId().equals(userId)) {
            throw new ApiException("You are not authorized to cancel this booking.");
        }

        String role = user.getRole();
        if (!(role.equals("PLAYER") || role.equals("PRO") || role.equals("PARENT"))) {
            throw new ApiException("Only players, pros, or parents can cancel bookings.");
        }

        if (!booking.getStatus().equalsIgnoreCase("PENDING")) {
            throw new ApiException("Only bookings with status PENDING can be cancelled.");
        }

        LocalDateTime sessionStart = booking.getSession().getStartDate();
        LocalDateTime now = LocalDateTime.now();

        if (!now.isBefore(sessionStart.minusHours(1))) {
            throw new RuntimeException("Cannot cancel booking less than 1 hour before the session.");
        }

        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
    }

}
