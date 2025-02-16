package com.assignment.question;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.springframework.boot.logging.LogLevel;

public class LoggerImpl implements Logger {
    private PrintWriter printWriter;  // Part 2: v1 - using PrintWriter
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
        if (this.printWriter == null)
            throw new IllegalStateException("Logger is not initialized!");
        this.printWriter.println(java.time.ZonedDateTime.now() + " " + level + " " + message);
    }

    @Override
    public void setLogFile(String filePath) {
        try {
            this.printWriter = new PrintWriter(filePath);
            this.filePath = filePath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public String getLogFile() {
        return this.filePath;
    }

    @Override
    public void flush() {
        if (this.printWriter == null)
            throw new IllegalStateException("Logger is not initialized. Cannot flush!");
        this.printWriter.flush();
    }

    @Override
    public void close() {
        if (this.printWriter == null)
            throw new IllegalStateException("Logger is not initialized. Cannot close!");
        this.printWriter.close();
        this.printWriter = null;
    }
}