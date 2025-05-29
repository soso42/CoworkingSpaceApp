package org.example.repository.impl;

import org.example.entity.Booking;
import org.example.repository.BookingRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void save(Booking booking) {
        Long lastId = this.bookings.getLast().getId();
        booking.setId(lastId + 1);
        this.bookings.add(booking);
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return this.bookings.stream()
                .filter(booking -> booking.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Booking> findAll() {
        return this.bookings;
    }

    @Override
    public void delete(Booking booking) {
        bookings.remove(booking);
    }


    public static InMemoryBookingRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InMemoryBookingRepository();
        }
        return INSTANCE;
    }

}
