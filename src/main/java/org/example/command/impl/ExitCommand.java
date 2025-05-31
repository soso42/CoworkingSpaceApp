package org.example.command.impl;

import org.example.command.Command;
import org.example.utils.FileManager;

public class ExitCommand implements Command {

    @Override
    public void execute() {
        FileManager.persistAllData();
        System.exit(0);
    }

}
