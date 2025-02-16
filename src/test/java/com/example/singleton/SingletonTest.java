package com.example.singleton;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for Singleton.
 */
class SingletonTest {
    @Test
    void testSingleton() {
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();

        assertSame(instance1, instance2);
    }

    @Test
    void testSingletonNotNull() {
        assertNotNull(Singleton.getInstance());
    }

    @Test
    void testSingletonThreadSafety() throws InterruptedException {
        final int POOL_SIZE = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);
        // Thread safe collection - used to store the instances obtained by each thread
        ConcurrentHashMap<Singleton, Boolean> instanceMap = new ConcurrentHashMap<>();

        // define the task
        Runnable task = () -> {
            Singleton instance = Singleton.getInstance();
            instanceMap.put(instance, true);
        };

        // execute tasks
        for (int i = 0; i < POOL_SIZE; i++)
            executorService.submit(task);
        
        // wait for all the tasks to finish
        executorService.shutdown();
        boolean areAllTasksFinished = executorService.awaitTermination(10, TimeUnit.SECONDS);

        assertTrue(areAllTasksFinished, "Are all tasks finished?");
        assertEquals(1, instanceMap.size());
    }
}
