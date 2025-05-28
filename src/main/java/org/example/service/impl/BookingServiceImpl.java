package org.example.service.impl;

import org.example.entity.Booking;
import org.example.exceptions.BookingNotAvailableException;
import org.example.repository.BookingRepository;
import org.example.repository.impl.InMemoryBookingRepository;
import org.example.service.BookingService;

import java.util.List;

public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository = InMemoryBookingRepository.getInstance();


    @Override
    public void save(Booking booking) {
        if (bookingOverlap(booking)) {
            throw new BookingNotAvailableException("Booking overlaps another booking");
        }
        bookingRepository.save(booking);
    }

    private boolean bookingOverlap(Booking booking) {
        return bookingRepository.findAll().stream()
                .filter(b -> b.getWorkSpaceId().equals(booking.getWorkSpaceId()))
                .anyMatch(b -> b.overlaps(booking.getStartDate(), booking.getEndDate()));
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }



}
