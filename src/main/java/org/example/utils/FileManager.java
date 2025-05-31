package org.example.utils;

import org.example.entity.Booking;
import org.example.entity.WorkSpace;
import org.example.repository.impl.InMemoryBookingRepository;
import org.example.repository.impl.InMemoryWorkSpaceRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileManager {

    private static final String WORKSPACES_FILE = "workspacesDB";
    private static final String BOOKINGS_FILE = "bookingsDB";

    private static final InMemoryBookingRepository bookingRepository = InMemoryBookingRepository.getInstance();
    private static final InMemoryWorkSpaceRepository workSpaceRepository = InMemoryWorkSpaceRepository.getInstance();


    public static void persistAllData() {
        List<WorkSpace> workspaces = workSpaceRepository.getWorkspaces();
        persist(workspaces, WORKSPACES_FILE);
        List<Booking> bookings = bookingRepository.getBookings();
        persist(bookings, BOOKINGS_FILE);
    }

    private static void persist(List<? extends Serializable> serializables, String fileName) {
        try (FileOutputStream file = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(file)) {

            for (Serializable s : serializables) {
                out.writeObject(s);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O exception: " + e.getMessage());
        }
    }

    public static void fetchData() {
        fetchBookings(fetchDataFromFile(BOOKINGS_FILE));
        fetchWorkspaces(fetchDataFromFile(WORKSPACES_FILE));
    }

    public static void fetchBookings(List<Serializable> serializables) {
        List<Booking> bookings = serializables.stream()
                .map(s -> (Booking) s)
                .collect(Collectors.toCollection(ArrayList::new));
        bookingRepository.setBookings(bookings);
    }

    public static void fetchWorkspaces(List<Serializable> serializables) {
        List<WorkSpace> workspaces = serializables.stream()
                .map(s -> (WorkSpace) s)
                .collect(Collectors.toCollection(ArrayList::new));
        workSpaceRepository.setWorkspaces(workspaces);
    }

    private static List<Serializable> fetchDataFromFile(String fileName) {
        List<Serializable> serializables = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(fileName);
             ObjectInputStream in = new ObjectInputStream(file)) {

            while (true) {
                try {
                    Serializable s = (Serializable) in.readObject();
                    serializables.add(s);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (EOFException e) {
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O exception: " + e.getMessage());
        }
        return serializables;
    }

}
