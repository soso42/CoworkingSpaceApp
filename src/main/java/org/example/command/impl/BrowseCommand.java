package org.example.command.impl;

import org.example.command.Command;
import org.example.repository.impl.JPAWorkSpaceRepository;
import org.example.service.WorkSpaceService;
import org.example.service.impl.WorkSpaceServiceImpl;

import java.util.Scanner;

public class BrowseCommand implements Command {

    private final WorkSpaceService workSpaceService = new WorkSpaceServiceImpl(JPAWorkSpaceRepository.getInstance());
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.println("\nHere is the list of available Coworking Spaces:\n");

        workSpaceService.findAll().stream()
                .filter(s -> s.getAvailable() == true)
                .forEach(System.out::println);

        System.out.println("\nHit enter to continue...");
        scanner.nextLine();
    }

}
