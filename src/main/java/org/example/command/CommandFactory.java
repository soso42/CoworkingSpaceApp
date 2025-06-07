package org.example.command;

import org.example.command.impl.*;
import org.example.exceptions.UnknownCommandException;

import java.util.Map;

public class CommandFactory {

    private final Map<String, Command> commands = Map.of(
        "admin", new AdminCommand(),
        "add", new AddCommand(),
        "remove", new RemoveCommand(),
        "view all", new ViewAllCommand(),

        "user", new UserCommand(),
        "browse", new BrowseCommand(),
        "book", new BookCommand(),
        "view", new ViewCommand(),
        "cancel", new CancelCommand(),

        "exit", new ExitCommand()
    );

    public Command getCommand(String commandName) {
        Command command = commands.get(commandName);
        if (command == null) {
            throw new UnknownCommandException("Command is unknown: " + commandName);
        }
        return command;
    }

}
