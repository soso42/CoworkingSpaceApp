package org.example.repository.impl;

import org.example.entity.Booking;
import org.example.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryBookingRepositoryTest {

    private BookingRepository bookingRepository;

    @BeforeEach
    void setUp() {
        this.bookingRepository = InMemoryBookingRepository.getInstance();
    }


    @Test
    void save_happyPath() {
        // Given
        Booking booking = Booking.builder()
                .workSpaceId(1L)
                .startDate(LocalDate.parse("2027-01-01"))
                .endDate(LocalDate.parse("2027-02-02"))
                .build();

        // When
        Booking savedBooking = bookingRepository.save(booking);

        // Then
        assertNotNull(savedBooking);
    }

    @Test
    void findById_happyPath() {
        // Given
        Booking booking = Booking.builder()
                .workSpaceId(1L)
                .startDate(LocalDate.parse("2027-01-01"))
                .endDate(LocalDate.parse("2027-02-02"))
                .build();
        Booking savedBooking = bookingRepository.save(booking);

        // When
        Optional<Booking> foundBooking = bookingRepository.findById(savedBooking.getId());

        // Then
        assertTrue(foundBooking.isPresent());
    }

    @ParameterizedTest
    @ValueSource(longs = {Long.MIN_VALUE, Long.MAX_VALUE})
    void findById_whenIdNotExists_returnsEmptyOptional(Long id) {
        // Given
        // When
        Optional<Booking> optBooking = bookingRepository.findById(id);

        // Then
        assertTrue(optBooking.isEmpty());
    }

    @Test
    void findAll_happyPath() {
        // Given
        int initialSize = bookingRepository.findAll().size();
        bookingRepository.save(new Booking());
        bookingRepository.save(new Booking());

        // When
        int actualSize = bookingRepository.findAll().size();

        // Then
        assertEquals(initialSize + 2, actualSize);
    }

    @Test
    void delete_happyPath() {
        // Given
        Booking booking = Booking.builder()
                .workSpaceId(1L)
                .startDate(LocalDate.parse("2027-01-01"))
                .endDate(LocalDate.parse("2027-02-02"))
                .build();
        Booking savedBooking = bookingRepository.save(booking);

        // When
        bookingRepository.delete(savedBooking);
        Optional<Booking> foundBooking = bookingRepository.findById(savedBooking.getId());

        // Then
        assertTrue(foundBooking.isEmpty());
    }


    @Test
    void getInstance_happyPath() {
        // Given
        // When
        BookingRepository repository1 = InMemoryBookingRepository.getInstance();
        BookingRepository repository2 = InMemoryBookingRepository.getInstance();

        // Then
        assertEquals(repository1, repository2);
    }

}
