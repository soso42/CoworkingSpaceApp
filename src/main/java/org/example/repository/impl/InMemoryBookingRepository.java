package org.example.repository.impl;

import org.example.entity.Booking;
import org.example.repository.BookingRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InMemoryBookingRepository implements BookingRepository {

    private static InMemoryBookingRepository INSTANCE;
    private List<Booking> bookings = new ArrayList<>();


    private InMemoryBookingRepository() {
        bookings.add(new Booking(101L,
                1L,
                LocalDate.of(2025, 5, 29),
                LocalDate.of(2025, 5, 31)));
    }


    @Override
    public List<Booking> findAll() {
        return this.bookings;
    }


    public static InMemoryBookingRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InMemoryBookingRepository();
        }
        return INSTANCE;
    }

}
