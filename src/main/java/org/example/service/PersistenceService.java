package org.example.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface PersistenceService {
    void saveToFile(Map<Long, ? extends Serializable> list, String fileName);
    <T extends Serializable> Map<Long, T> readFromFile(String fileName);
}
