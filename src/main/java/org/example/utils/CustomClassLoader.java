package org.example.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomClassLoader extends ClassLoader {

    private static final String DIRECTORY = "ext";

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {

        Class<?> loadedClass = findLoadedClass(name);

        if (loadedClass == null) {
            try {
                loadedClass = super.loadClass(name, resolve);
            } catch (ClassNotFoundException e) {
                loadedClass = load(name);
            }
        }

        if (resolve) {
            resolveClass(loadedClass);
        }

        return loadedClass;
    }

    private Class<?> load(String name) {
        String file = DIRECTORY + File.separator + name + ".class";
        byte[] bytes;
        try (FileInputStream fis = new FileInputStream(file)) {
            bytes = fis.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return defineClass(name, bytes, 0, bytes.length);
    }


    public static void main(String[] args) {
        try {

            Class<?> clazz = new CustomClassLoader().loadClass("Printer");
            Object ob = clazz.getConstructor().newInstance();
            Method method = clazz.getMethod("print");
            method.invoke(ob);

        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e.getMessage());
        } catch (InvocationTargetException e) {
            System.out.println("InvocationTargetException: " + e.getMessage());
        } catch (InstantiationException e) {
            System.out.println("InstantiationException:" + e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException: " + e.getMessage());
        } catch (NoSuchMethodException e) {
            System.out.println("NoSuchMethodException: " + e.getMessage());
        }
    }

}
