package org.example.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookingTest {

    @Test
    void overlaps_happyPath_allDatesBefore() {
        // Given
        Booking booking = new Booking();
        booking.setStartDate(LocalDate.of(2020, 2, 5));
        booking.setEndDate(LocalDate.of(2020, 2, 11));
        LocalDate start = LocalDate.of(2020, 2, 1);
        LocalDate end = LocalDate.of(2020, 2, 4);
        // When
        boolean overlaps = booking.overlaps(start, end);
        // Then
        assertFalse(overlaps);
    }

    @Test
    void overlaps_happyPath_allDatesAfter() {
        // Given
        Booking booking = new Booking();
        booking.setStartDate(LocalDate.of(2020, 2, 5));
        booking.setEndDate(LocalDate.of(2020, 2, 11));
        LocalDate start = LocalDate.of(2020, 2, 12);
        LocalDate end = LocalDate.of(2020, 2, 15);
        // When
        boolean overlaps = booking.overlaps(start, end);
        // Then
        assertFalse(overlaps);
    }

    @Test
    void overlaps_startDateOverlaps() {
        // Given
        Booking booking = new Booking();
        booking.setStartDate(LocalDate.of(2020, 2, 5));
        booking.setEndDate(LocalDate.of(2020, 2, 11));
        LocalDate start = LocalDate.of(2020, 2, 11);
        LocalDate end = LocalDate.of(2020, 2, 15);
        // When
        boolean overlaps = booking.overlaps(start, end);
        // Then
        assertTrue(overlaps);
    }

    @Test
    void overlaps_endDateOverlaps() {
        // Given
        Booking booking = new Booking();
        booking.setStartDate(LocalDate.of(2020, 2, 5));
        booking.setEndDate(LocalDate.of(2020, 2, 11));
        LocalDate start = LocalDate.of(2020, 2, 1);
        LocalDate end = LocalDate.of(2020, 2, 5);
        // When
        boolean overlaps = booking.overlaps(start, end);
        // Then
        assertTrue(overlaps);
    }

    @Test
    void overlaps_bothDatesOverlap() {
        // Given
        Booking booking = new Booking();
        booking.setStartDate(LocalDate.of(2020, 2, 5));
        booking.setEndDate(LocalDate.of(2020, 2, 11));
        LocalDate start = LocalDate.of(2020, 2, 6);
        LocalDate end = LocalDate.of(2020, 2, 10);
        // When
        boolean overlaps = booking.overlaps(start, end);
        // Then
        assertTrue(overlaps);
    }

}
