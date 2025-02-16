package com.assignment.question;

import java.util.Properties;

/**
 * Part 1: Implementing Singleton - v1
 * - Double Checked Lock (DCL)
 * 
 * Part 2: Implementing Configuration Management - v1
 */

public class FileBasedConfigurationManagerImpl extends FileBasedConfigurationManager {
    // step 1: private static instance
    private static volatile FileBasedConfigurationManagerImpl instance;

    // step 2: private CTOR
    private FileBasedConfigurationManagerImpl() {
        // initialization implementation
        super();  // calls the CTOR of the superclass
    }

    @Override
    public String getConfiguration(String key) {
        Properties properties = this.getProperties();
        return properties.getProperty(key);
    }

    @Override
    public <T> T getConfiguration(String key, Class<T> type) {
        Properties properties = this.getProperties();
        String propertyVal = properties.getProperty(key);
        if (propertyVal == null)
            return null;
        return this.convert(propertyVal, type);
    }

    @Override
    public void setConfiguration(String key, String value) {
        this.getProperties().setProperty(key, value);
    }

    @Override
    public <T> void setConfiguration(String key, T value) {
        if (value == null)
            this.getProperties().setProperty(key, null);
        else if (this.checkType(value))
            this.getProperties().setProperty(key, value.toString());
        else
            throw new RuntimeException("Invalid value type!");
    }

    private <T> boolean checkType(T value) {
        Class<?> klass = value.getClass();
        switch (klass.getSimpleName()) {
            case "Integer":
            case "Long":
            case "Float":
            case "Double":
                return true;
            default:
                return false;
        }
    }

    @Override
    public void removeConfiguration(String key) {
        this.getProperties().remove(key);
    }

    @Override
    public void clear() {
        this.getProperties().clear();
    }

    public static FileBasedConfigurationManager getInstance() {
        if (instance == null)
            synchronized (FileBasedConfigurationManagerImpl.class) {
                if (instance == null)
                    instance = new FileBasedConfigurationManagerImpl();
            }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

}