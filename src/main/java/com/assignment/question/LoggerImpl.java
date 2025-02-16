package com.assignment.question;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.boot.logging.LogLevel;

public class LoggerImpl implements Logger {
    private FileWriter fileWriter;  // Part 2: v2 - using FileWriter
    private String filePath;

    // Part 1: Implement Singleton - DCL (Double Checked Lock)
    // step 1: declare a private static var of type LoggerImpl
    private static LoggerImpl instance;

    // step 2: make the CTOR private
    private LoggerImpl() {
        // initialization logic
    }

    // step 3: provide a global access interface
    public static LoggerImpl getInstance() {
        if (instance == null)
            synchronized (LoggerImpl.class) {
                if (instance == null)
                    instance = new LoggerImpl();
            }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    // Part 2: Implement Logger
    @Override
    public void log(LogLevel level, String message) {
        if (this.fileWriter == null)
            throw new IllegalStateException("Logger is not initialized!");
        try {
            this.fileWriter.write(java.time.ZonedDateTime.now() + " " + level + " " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setLogFile(String filePath) {
        try {
            this.fileWriter = new FileWriter(filePath);
            this.filePath = filePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public String getLogFile() {
        return this.filePath;
    }

    @Override
    public void flush() {
        if (this.fileWriter == null)
            throw new IllegalStateException("Logger is not initialized. Cannot flush!");
        try {
            this.fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (this.fileWriter == null)
            throw new IllegalStateException("Logger is not initialized. Cannot close!");
        try {
            this.fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.fileWriter = null;
    }
}