package org.example.repository.impl;

import org.example.entity.Booking;
import org.example.repository.BookingRepository;

import java.util.List;
import java.util.Optional;

public class JPABookingRepository implements BookingRepository {

    private static JPABookingRepository instance;

    private JPABookingRepository() {}


    @Override
    public Booking save(Booking booking) {
        return null;
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Booking> findAll() {
        return List.of();
    }

    @Override
    public void delete(Booking booking) {

    }


    public static JPABookingRepository getInstance() {
        if  (instance == null) {
            instance = new JPABookingRepository();
        }
        return instance;
    }

}
