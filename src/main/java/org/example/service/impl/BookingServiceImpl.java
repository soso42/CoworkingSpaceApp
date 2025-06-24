package org.example.service.impl;

import lombok.AllArgsConstructor;
import org.example.entity.Booking;
import org.example.exceptions.BookingNotAvailableException;
import org.example.exceptions.BookingNotFoundException;
import org.example.repository.BookingRepository;
import org.example.service.BookingService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;


    @Override
    public void book(Booking booking) {
        if (bookingOverlapsOthers(booking)) {
            throw new BookingNotAvailableException("Booking overlaps another booking");
        }
        bookingRepository.save(booking);
    }

    private boolean bookingOverlapsOthers(Booking booking) {
        return bookingRepository.findAll().stream()
                .filter(b -> b.getWorkSpace().getId().equals(booking.getWorkSpace().getId()))
                .anyMatch(b -> overlaps(b, booking));
    }

    private boolean overlaps(Booking b1, Booking b2) {
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
