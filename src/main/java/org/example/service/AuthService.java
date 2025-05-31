package org.example.service;

import org.example.enums.AccessLevel;

public interface AuthService {
    AccessLevel getAccessLevel();
    void setAccessLevel(AccessLevel accessLevel);
}
