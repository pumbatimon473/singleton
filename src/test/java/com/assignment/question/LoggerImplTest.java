package com.assignment.question;

import org.junit.jupiter.api.Test;
import org.springframework.boot.logging.LogLevel;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoggerImplTest {
    @Test
    void testFileWriter() throws IOException {
        LoggerImpl logger = LoggerImpl.getInstance();
        String file = "FileWriter.log";
        logger.setLogFile(file);
        assertEquals(file, logger.getLogFile());

        logger.log(LogLevel.INFO, "Gandhi Bot");
        logger.flush();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));) {
            String actual = bufferedReader.readLine();
            System.out.println("From log: '" + actual + "'");
            assertTrue(actual.contains("INFO Gandhi Bot"));
        }
        
        logger.close();
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            logger.log(LogLevel.ERROR, "Bappi Bapu");
        });
        assertEquals("Logger is not initialized!", exception.getMessage());
    }
}
