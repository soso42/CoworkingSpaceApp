package org.example.service.impl;

import org.example.entity.Booking;
import org.example.repository.BookingRepository;
import org.example.repository.impl.InMemoryBookingRepository;
import org.example.service.BookingService;

import java.util.List;

public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository = InMemoryBookingRepository.getInstance();


    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

}
