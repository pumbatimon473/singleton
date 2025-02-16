package com.example.singleton;

/**
 * Design Pattern: Singleton
 * - Type: Creational
 * 
 * Implementation: v5
 * - Taking leverage of class loading mechanism:
 *  -- JVM guarantees that classes are loaded in a thread safe manner (i.e., they are loaded only once)
 *  -- static inner classes are lazily loaded
 * 
 * Pros:
 * - Lazy initialization - The object is initialized on demand
 * - Thread Safe
 * - Does not need explicit synchronization
 * 
 */
class Singleton {
    // step 1: make the CTOR private
    private Singleton() {
        // object initialization logic
    }

    // step 2: make a static inner class
    private static class SingletonHolder {
        // initialize a static var of type Singleton
        private static final Singleton INSTANCE = new Singleton();
    }

    // step 3: provide a global access interface
    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
