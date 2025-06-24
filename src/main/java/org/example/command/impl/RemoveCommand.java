package org.example.command.impl;

import org.example.command.Command;
import org.example.exceptions.WorkSpaceNotFoundException;
import org.example.repository.impl.JPAWorkSpaceRepository;
import org.example.service.WorkSpaceService;
import org.example.service.impl.WorkSpaceServiceImpl;

import java.util.Scanner;

public class RemoveCommand implements Command {

    private final WorkSpaceService workSpaceService = new WorkSpaceServiceImpl(JPAWorkSpaceRepository.getInstance());
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.println("\nList of available WorkSpaces:\n");
        workSpaceService.findAll().forEach(System.out::println);
        System.out.println("\nPlease enter the ID of the work space you want to remove: ");

        Long id = Long.parseLong(scanner.nextLine());

        try {
            workSpaceService.removeWorkSpace(id);
            System.out.println("\nWork space deleted successfully!");
        } catch (WorkSpaceNotFoundException e) {
            System.out.println("The work space with the ID: " + id + " does not exist");
        }

        System.out.println("\nEnter to continue...");
        scanner.nextLine();
    }

}
