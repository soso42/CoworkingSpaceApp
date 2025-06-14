package org.example.service.impl;

import org.example.entity.Booking;
import org.example.exceptions.BookingNotAvailableException;
import org.example.exceptions.BookingNotFoundException;
import org.example.repository.BookingRepository;
import org.example.repository.impl.InMemoryBookingRepository;
import org.example.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {

    private BookingRepository bookingRepository;
    private BookingService bookingService;


    @BeforeEach
    void setUp() {
        this.bookingRepository = mock(InMemoryBookingRepository.class);
        this.bookingService = new BookingServiceImpl(bookingRepository);
    }


    @Test
    void book_happyPath() {
        // Given
        Booking booking1 = Booking.builder()
                .workSpaceId(1L)
                .startDate(LocalDate.parse("2027-01-01"))
                .endDate(LocalDate.parse("2027-02-02"))
                .build();
        Booking booking2 = Booking.builder()
                .workSpaceId(1L)
                .startDate(LocalDate.parse("2026-01-01"))
                .endDate(LocalDate.parse("2026-02-02"))
                .build();
        when(bookingRepository.findAll()).thenReturn(List.of(booking1));

        // When
        // Then
        assertDoesNotThrow(() -> bookingService.book(booking2));
    }

    @Test
    void book_whenBookingsOverlap_throwException() {
        // Given
        Booking booking1 = Booking.builder()
                .workSpaceId(1L)
                .startDate(LocalDate.parse("2027-01-01"))
                .endDate(LocalDate.parse("2027-02-02"))
                .build();
        Booking booking2 = Booking.builder()
                .workSpaceId(1L)
                .startDate(LocalDate.parse("2027-01-01"))
                .endDate(LocalDate.parse("2027-02-02"))
                .build();
        when(bookingRepository.findAll()).thenReturn(List.of(booking1));

        // When
        // Then
        assertThrows(BookingNotAvailableException.class, () -> bookingService.book(booking2));
    }

    @Test
    void findAll() {
        // Given
        List<Booking> bookings = List.of(new Booking(), new Booking());
        when(bookingRepository.findAll()).thenReturn(bookings);

        // When
        int result = bookingService.findAll().size();

        // Then
        assertEquals(bookings.size(), result);
    }

    @Test
    void cancelBooking_happyPath() {
        // Given
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(new Booking()));

        // When
        // Then
        assertDoesNotThrow(() -> bookingService.cancelBooking(111L));
    }

    @Test
    void cancelBooking_whenIdIncorrect() {
        // Given
        Long mockId = Long.MAX_VALUE;
        when(bookingRepository.findById(mockId)).thenReturn(Optional.empty());

        // When
        // Then
        assertThrows(BookingNotFoundException.class, () -> bookingService.cancelBooking(mockId));
    }

}
