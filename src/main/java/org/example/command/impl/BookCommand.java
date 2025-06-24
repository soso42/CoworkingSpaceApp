package org.example.command.impl;

import org.example.command.Command;
import org.example.entity.Booking;
import org.example.exceptions.BookingNotAvailableException;
import org.example.repository.impl.JPABookingRepository;
import org.example.repository.impl.JPAWorkSpaceRepository;
import org.example.service.BookingService;
import org.example.service.WorkSpaceService;
import org.example.service.impl.BookingServiceImpl;
import org.example.service.impl.WorkSpaceServiceImpl;

import java.time.LocalDate;
import java.util.Scanner;

public class BookCommand implements Command {

    private final BookingService bookingService = new BookingServiceImpl(JPABookingRepository.getInstance());
    private final WorkSpaceService workSpaceService = new WorkSpaceServiceImpl(JPAWorkSpaceRepository.getInstance());
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {

        System.out.println("\nList of Co-working spaces:\n");
        workSpaceService.findAll().forEach(System.out::println);

        System.out.println("\nPlease enter ID of desired Co-working space:");
        Long id = Long.parseLong(scanner.nextLine());

        System.out.println("\nPlease enter START date: (format: YYYY-MM-DD)");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());

        System.out.println("\nPlease enter END date: (format: YYYY-MM-DD)");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        Booking booking = new Booking(null, null, startDate, endDate);
        try {
            bookingService.book(booking);
            System.out.println("\nBooking successfully made");
        } catch (BookingNotAvailableException e) {
            System.out.println("\nBooking not available for your chosen dates...");
        }

        System.out.println("\nEnter to continue...");
        scanner.nextLine();
    }

}
