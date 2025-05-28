package org.example.service;

import org.example.entity.Booking;

import java.util.List;

public interface BookingService {
    List<Booking> findAll();
}
