package org.example;

import org.example.command.Command;
import org.example.command.CommandFactory;
import org.example.enums.AccessLevel;
import org.example.exceptions.UnknownCommandException;
import org.example.service.AuthService;
import org.example.service.impl.AuthServiceImpl;

import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        AuthService authService = AuthServiceImpl.getInstance();
        Scanner scanner = new Scanner(System.in);
        CommandFactory commandFactory = new CommandFactory();
        String input;
        Command command;

        Map<AccessLevel, Runnable> menus = Map.of(
                AccessLevel.ADMIN, Main::displayAdminMenu,
                AccessLevel.USER, Main::displayUserMenu,
                AccessLevel.NONE, Main::displayMainMenu
        );

        while (true) {
            AccessLevel accessLevel = authService.getAccessLevel();
            menus.get(accessLevel).run();

            input = scanner.nextLine();

            try {
                command = commandFactory.getCommand(input);
                command.execute();
            } catch (UnknownCommandException e) {
                System.out.println("\nUNKNOWN COMMAND. Please try again\n");
            }
        }

    }

    public static void displayMainMenu() {
        System.out.println();
        System.out.println("Welcome to the main menu!");
        System.out.println();
        System.out.println("list of available commands:");
        System.out.println();
        System.out.println("admin  - logs you in as ADMIN");
        System.out.println("user   - logs you in as USER");
        System.out.println("exit   - exits the program");
        System.out.println();
        System.out.println("Please type a command to continue: ");
    }

    public static void displayAdminMenu() {
        System.out.println();
        System.out.println("Welcome to the ADMIN menu!");
        System.out.println();
        System.out.println("list of available commands:");
        System.out.println();
        System.out.println("add       - adds a new coworking space");
        System.out.println("remove    - removes a coworking space");
        System.out.println("view all  - view all reservations");
        System.out.println("exit      - exits the program");
        System.out.println();
        System.out.println("Please type a command to continue: ");
    }

    public static void displayUserMenu() {
        System.out.println();
        System.out.println("Welcome to the USER menu!");
        System.out.println();
        System.out.println("list of available commands:");
        System.out.println();
        System.out.println("browse    - browse available spaces");
        System.out.println("book      - make a reservation");
        System.out.println("view      - view my reservations");
        System.out.println("cancel    - cancel a reservations");
        System.out.println("exit      - exits the program");
        System.out.println();
        System.out.println("Please type a command to continue: ");
    }

}
