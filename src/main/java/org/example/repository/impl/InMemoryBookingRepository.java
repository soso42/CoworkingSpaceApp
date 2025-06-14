package org.example.repository.impl;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.Booking;
import org.example.repository.BookingRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class InMemoryBookingRepository implements BookingRepository {

    private static InMemoryBookingRepository INSTANCE;
    private Map<Long, Booking> bookings = new HashMap<>();

    private InMemoryBookingRepository() {}


    @Override
    public Booking save(Booking booking) {
        Long lastId = this.bookings.keySet().stream()
                        .max(Long::compareTo)
                        .orElse(0L);
        booking.setId(lastId + 1);
        this.bookings.put(booking.getId(), booking);
        return booking;
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return Optional.ofNullable(this.bookings.get(id));
    }

    @Override
    public List<Booking> findAll() {
        return this.bookings.values().stream().toList();
    }

    @Override
    public void delete(Booking booking) {
        bookings.remove(booking.getId());
    }


    public static InMemoryBookingRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InMemoryBookingRepository();
        }
        return INSTANCE;
    }

}
