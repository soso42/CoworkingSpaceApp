package org.example.service.impl;

import lombok.AllArgsConstructor;
import org.example.entity.Booking;
import org.example.entity.WorkSpace;
import org.example.repository.impl.InMemoryBookingRepository;
import org.example.repository.impl.InMemoryWorkSpaceRepository;
import org.example.service.AppStateService;
import org.example.service.PersistenceService;

import java.util.Map;

@AllArgsConstructor
public class AppStateServiceImpl implements AppStateService {

    private static final String WORKSPACES_FILE = "workspacesDB";
    private static final String BOOKINGS_FILE = "bookingsDB";

    private final InMemoryBookingRepository bookingRepository;
    private final InMemoryWorkSpaceRepository workSpaceRepository;
    private final PersistenceService persistenceService;

    @Override
    public void saveAllData() {
        persistenceService.saveToFile(bookingRepository.getBookings(), BOOKINGS_FILE);
        persistenceService.saveToFile(workSpaceRepository.getWorkspaces(), WORKSPACES_FILE);
    }

    @Override
    public void restoreAllData() {
        Map<Long, Booking> bookings = persistenceService.readFromFile(BOOKINGS_FILE);
        bookingRepository.setBookings(bookings);
        Map<Long, WorkSpace> workspaces = persistenceService.readFromFile(WORKSPACES_FILE);
        workSpaceRepository.setWorkspaces(workspaces);
    }

}
