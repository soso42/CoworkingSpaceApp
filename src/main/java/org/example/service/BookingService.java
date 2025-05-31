package org.example.service;

import org.example.entity.Booking;

import java.util.List;

public interface BookingService {
    void book(Booking booking);
    List<Booking> findAll();
    void cancelBooking(Long id);
}
