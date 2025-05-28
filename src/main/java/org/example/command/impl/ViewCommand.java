package org.example.command.impl;

import org.example.command.Command;
import org.example.service.WorkSpaceService;
import org.example.service.impl.WorkSpaceServiceImpl;

import java.util.Scanner;

public class ViewCommand implements Command {

    private final WorkSpaceService workSpaceService = new WorkSpaceServiceImpl();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {

//        System.out.println("List of work spaces:");
//
//        workSpaceService.findAll().forEach(System.out::println);
//
//        System.out.println("Press enter to continue...");
//        scanner.nextLine();
        System.out.println("view all reservations");

    }

}
