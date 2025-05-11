package com.levelup.levelup_academy.Service;

import com.levelup.levelup_academy.Api.ApiException;
import com.levelup.levelup_academy.DTO.EmailRequest;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SessionRepository sessionRepository;
    private final AuthRepository authRepository;
    private final EmailNotificationService emailNotificationService;


    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public void addBooking(Integer userId, Integer sessionId){
        User user = authRepository.findUserById(userId);
        if(user == null) throw new ApiException("User not found");

        Session session = sessionRepository.findSessionById(sessionId);
        if(session == null) throw new ApiException("Session not found");

        Booking booking = new Booking();
        booking.setBookDate(LocalDate.now());
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

}
