package org.example.command.impl;

import org.example.command.Command;
import org.example.repository.impl.InMemoryBookingRepository;
import org.example.repository.impl.InMemoryWorkSpaceRepository;
import org.example.service.AppStateService;
import org.example.service.impl.AppStateServiceImpl;
import org.example.service.impl.PersistenceServiceImpl;

public class ExitCommand implements Command {

    @Override
    public void execute() {

        AppStateService appStateService = new AppStateServiceImpl(
                        InMemoryBookingRepository.getInstance(),
                        InMemoryWorkSpaceRepository.getInstance(),
                        new PersistenceServiceImpl());

        appStateService.saveAllData();

        System.exit(0);
    }

}
