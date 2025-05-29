package org.example.service.impl;

import org.example.entity.Booking;
import org.example.exceptions.BookingNotAvailableException;
import org.example.exceptions.BookingNotFoundException;
import org.example.repository.BookingRepository;
import org.example.repository.impl.InMemoryBookingRepository;
import org.example.service.BookingService;

import java.util.List;
import java.util.Optional;

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
                .anyMatch(b -> overlaps(b, booking));
    }

    public boolean overlaps(Booking b1, Booking b2) {
        return !b1.getStartDate().isAfter(b2.getEndDate()) && !b1.getEndDate().isBefore(b2.getStartDate());
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public void cancelBooking(Long id) {
        Optional<Booking> optBooking = this.bookingRepository.findById(id);
        if (optBooking.isEmpty()) {
            throw new BookingNotFoundException("Booking not found");
        }
        this.bookingRepository.delete(optBooking.get());
    }

}
