package org.example.repository;

import org.example.entity.Booking;

import java.util.List;

public interface BookingRepository {
    List<Booking> findAll();
}
