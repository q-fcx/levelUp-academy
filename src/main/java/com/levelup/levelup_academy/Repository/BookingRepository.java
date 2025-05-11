package com.levelup.levelup_academy.Repository;

import com.levelup.levelup_academy.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Booking findBookingById(Integer id);
    List<Booking> findBookingBySessionId(Integer id);
}
