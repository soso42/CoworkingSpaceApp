package org.example.command.impl;

import org.example.command.Command;
import org.example.service.AppStateService;
import org.example.service.impl.AppStateServiceImpl;

public class ExitCommand implements Command {

    @Override
    public void execute() {
        System.exit(0);
    }

}
