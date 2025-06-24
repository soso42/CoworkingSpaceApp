package org.example.command.impl;

import org.example.command.Command;
import org.example.repository.impl.JPABookingRepository;
import org.example.service.BookingService;
import org.example.service.impl.BookingServiceImpl;

import java.util.Scanner;

public class ViewCommand implements Command {

    private final BookingService bookingService = new BookingServiceImpl(JPABookingRepository.getInstance());
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {

        System.out.println("\nList of all my reservations\n");

        bookingService.findAll().forEach(System.out::println);

        System.out.println("\nPress enter to continue...");
        scanner.nextLine();
    }

}
