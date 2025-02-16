package com.example.singleton;

/**
 * Design Pattern: Singleton
 * - Type: Creational
 * 
 * Implementation: v3
 * - Using synchronized method
 * 
 * Pros:
 * - Lazy initialization - The object is initialized on demand
 * - Thread Safe
 * 
 * Cons:
 * - Allows only 1 thread to access the method: getInstance()
 *  -- Slow
 */
class Singleton {
    // step 1: declare a private static var of type Singleton
    private static Singleton instance;

    // step 2: make the CTOR private
    private Singleton() {
        // object initialization logic
    }

    // step 3: provide a global access interface
    public static synchronized Singleton getInstance() {
        if (instance == null)
            instance = new Singleton();
        return instance;
    }
}
