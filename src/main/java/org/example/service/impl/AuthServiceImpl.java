package org.example.service.impl;

import org.example.enums.AccessLevel;
import org.example.service.AuthService;


public class AuthServiceImpl implements AuthService {

    private static AuthService authService;

    private AccessLevel accessLevel = AccessLevel.NONE;

    private AuthServiceImpl() {}

    @Override
    public AccessLevel getAccessLevel() {
        return this.accessLevel;
    }

    @Override
    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public static AuthService getInstance() {
        if (authService == null) {
            authService = new AuthServiceImpl();
        }
        return authService;
    }

}
