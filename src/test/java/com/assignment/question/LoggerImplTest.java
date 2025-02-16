package com.assignment.question;

import org.junit.jupiter.api.Test;
import org.springframework.boot.logging.LogLevel;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoggerImplTest {
    @Test
    void testPrintWriter() throws IOException {
        LoggerImpl logger = LoggerImpl.getInstance();
        String file = "PrintWriter.log";
        logger.setLogFile(file);
        assertEquals(file, logger.getLogFile());

        logger.log(LogLevel.INFO, "Gandhi II");
        logger.flush();
        Path filePath = Paths.get(file);
        System.out.println("Logger filePath: " + filePath);
        String actual = new String(Files.readAllBytes(filePath));
        System.out.println("From log: '" + actual + "'");
        assertTrue(actual.contains("INFO Gandhi II"));

        logger.close();
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            logger.log(LogLevel.ERROR, "Bapu");
        });
        assertEquals("Logger is not initialized!", exception.getMessage());
    }
}
