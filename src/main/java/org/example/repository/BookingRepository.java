package org.example.repository;

import org.example.entity.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    Booking save(Booking booking);
    Optional<Booking> findById(Long id);
    List<Booking> findAll();
    void delete(Booking booking);
}
