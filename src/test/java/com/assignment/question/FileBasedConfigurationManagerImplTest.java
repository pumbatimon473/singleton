package com.assignment.question;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileBasedConfigurationManagerImplTest {
    @Test
    public void testSingleton() {
        FileBasedConfigurationManager instance1 = FileBasedConfigurationManagerImpl.getInstance();
        FileBasedConfigurationManager instance2 = FileBasedConfigurationManagerImpl.getInstance();

        assertSame(instance1, instance2);
    }

    @Test
    public void testSingletonNotNull() {
        assertNotNull(FileBasedConfigurationManagerImpl.getInstance());
    }

    @Test
    public void testSingletonThreadSafe() throws InterruptedException {
        final int POOL_SIZE = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);
        ConcurrentHashMap<FileBasedConfigurationManager, Boolean> instanceMap = new ConcurrentHashMap<>();

        // Define task
        Runnable task = () -> {
            FileBasedConfigurationManager instance = FileBasedConfigurationManagerImpl.getInstance();
            instanceMap.put(instance, true);
        };

        // Running tasks
        for (int i = 0; i < POOL_SIZE; i++)
            executorService.submit(task);
        
        // Waiting for all the tasks to finish
        executorService.shutdown();
        boolean areTasksCompleted = executorService.awaitTermination(10, TimeUnit.SECONDS);

        assertTrue(areTasksCompleted, "Are all tasks completed?");
        assertEquals(1, instanceMap.size());
    }

    @Test
    public void testSetConfig() {
        FileBasedConfigurationManager configMan = FileBasedConfigurationManagerImpl.getInstance();
        configMan.setConfiguration("ram_in_gb", "8");
        String configVal = configMan.getConfiguration("ram_in_gb");

        assertEquals("8", configVal);
    }

    @Test
    public void testSetConfigWithType() {
        FileBasedConfigurationManager configMan = FileBasedConfigurationManagerImpl.getInstance();
        configMan.setConfiguration("cpu_base_clock", 2.30);
        Double configVal = configMan.getConfiguration("cpu_base_clock", Double.class);

        assertEquals(2.30, configVal, 0.0);
    }

    @Test
    public void testRemoveConfig() {
        FileBasedConfigurationManager configMan = FileBasedConfigurationManagerImpl.getInstance();
        configMan.setConfiguration("cpu", "Intel Core i9");
        assertTrue(configMan.getProperties().containsKey("cpu"));

        configMan.removeConfiguration("cpu");
        assertFalse(configMan.getProperties().containsKey("cpu"));
    }

    @Test
    public void testClearAllConfig() {
        FileBasedConfigurationManager configMan = FileBasedConfigurationManagerImpl.getInstance();
        configMan.setConfiguration("cpu", "Snapdragon X Elite");
        configMan.setConfiguration("npu", "45 TOPS");
        assertEquals(2, configMan.getProperties().size());

        configMan.clear();
        assertEquals(0, configMan.getProperties().size());
    }
}
