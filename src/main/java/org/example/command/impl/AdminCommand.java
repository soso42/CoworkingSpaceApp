package org.example.command.impl;

import org.example.command.Command;
import org.example.enums.AccessLevel;
import org.example.service.AuthService;
import org.example.service.impl.AuthServiceImpl;

public class AdminCommand implements Command {

    private final AuthService authService = AuthServiceImpl.getInstance();

    @Override
    public void execute() {
        this.authService.setAccessLevel(AccessLevel.ADMIN);
    }

}
