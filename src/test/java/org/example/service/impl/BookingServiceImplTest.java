package org.example.service.impl;

import org.example.entity.Booking;
import org.example.exceptions.BookingNotAvailableException;
import org.example.exceptions.BookingNotFoundException;
import org.example.repository.BookingRepository;
import org.example.repository.impl.InMemoryBookingRepository;
import org.example.service.BookingService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {

    private static BookingRepository mockBookingRepository = mock(InMemoryBookingRepository.class);
    private static BookingService mockBookingService = new BookingServiceImpl(mockBookingRepository);

    private static BookingService realBookingService = new BookingServiceImpl(InMemoryBookingRepository.getInstance());


    @Test
    void book_happyPath() {
        // Given
        int currentBookings = realBookingService.findAll().size();
        Booking booking1 = new Booking(null, 1L, LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1,  2));
        // When
        realBookingService.book(booking1);
        // Then
        assertEquals(currentBookings + 1, realBookingService.findAll().size());
    }

    @Test
    void book_whenBookingsOverlap() {
        // Given
        Booking booking1 = new Booking(null, 1L, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1,  2));
        Booking booking2 = new Booking(null, 1L, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1,  2));
        // When
        realBookingService.book(booking1);
        // Then
        assertThrows(BookingNotAvailableException.class, () -> realBookingService.book(booking2));
    }

    @Test
    void findAll() {
        // Given
        int currentBookings = realBookingService.findAll().size();
        Booking booking1 = new Booking(null, 1L, LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1,  2));
        Booking booking2 = new Booking(null, 1L, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1,  2));
        // When
        realBookingService.book(booking1);
        realBookingService.book(booking2);
        // Then
        assertEquals(currentBookings + 2, realBookingService.findAll().size());
    }

    @Test
    void cancelBooking_happyPath() {
        // Given
        // When
        when(mockBookingRepository.findById(111L)).thenReturn(Optional.of(new Booking()));
        // Then
        assertDoesNotThrow(() -> mockBookingService.cancelBooking(111L));
    }

    @Test
    void cancelBooking_whenIdIncorrect() {
        // Given
        Long mockId = Long.MAX_VALUE;
        // When
        when(mockBookingRepository.findById(mockId)).thenReturn(Optional.empty());
        // Then
        assertThrows(BookingNotFoundException.class, () -> mockBookingService.cancelBooking(mockId));
    }

}
