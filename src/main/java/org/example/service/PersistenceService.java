package org.example.service;

import java.io.Serializable;
import java.util.List;

public interface PersistenceService {
    void saveToFile(List<? extends Serializable> list, String fileName);
    <T extends Serializable> List<T> readFromFile(String fileName);
}
