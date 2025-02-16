package com.example.singleton;

/**
 * Design Pattern: Singleton
 * - Type: Creational
 * 
 * Implementation: v4
 * - Using synchronized block - Double Checked Lock (DCL)
 * 
 * Pros:
 * - Lazy initialization - The object is initialized on demand
 * - Thread Safe
 * - Faster than synchronized method
 * 
 * Cons:
 * - A bit complex
 */
class Singleton {
    // step 1: declare a private static var of type Singleton
    private static volatile Singleton instance;

    // step 2: make the CTOR private
    private Singleton() {
        // object initialization logic
    }

    // step 3: provide a global access interface
    public static Singleton getInstance() {
        if (instance == null)  // 1st check
            synchronized (Singleton.class) {  // acquiring lock only when instance is null
                if (instance == null)  // 2nd check
                    instance = new Singleton();
            }
        return instance;
    }
}
