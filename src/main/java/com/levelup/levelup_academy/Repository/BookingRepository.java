package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Booking;
import com.levelup.levelup_academy.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Booking findBookingById(Integer id);

    List<Booking> findByUserId(Integer userId);

    @Query("SELECT b.user FROM Booking b WHERE b.session.id = ?1")
    List<User> findUsersBySessionId(Integer sessionId);
}
