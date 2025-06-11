package org.example.service.impl;

import org.example.service.PersistenceService;

import java.io.*;
import java.util.List;
import java.util.Map;

public class PersistenceServiceImpl implements PersistenceService {

    @Override
    public void saveToFile(Map<Long, ? extends Serializable> list, String fileName) {
        try (FileOutputStream file = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(file)) {

            out.writeObject(list);

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O exception: " + e.getMessage());
        }
    }

    @Override
    public <T extends Serializable> Map<Long, T> readFromFile(String fileName) {
        Map<Long, T> data = null;
        try (FileInputStream file = new FileInputStream(fileName);
             ObjectInputStream in = new ObjectInputStream(file)) {

            Object result = in.readObject();
            data = (Map<Long, T>) result;

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O exception: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e.getMessage());
        } catch (ClassCastException e) {
            System.out.println("ClassCastException: " + e.getMessage());
        }
        return data;
    }

}
