package com.example.singleton;

/**
 * Design Pattern: Singleton
 * - Type: Creational
 * 
 * Implementation: v1
 * - Very crude implementation - Not Thread Safe
 * 
 * Pros:
 * - Simple
 * 
 * Cons:
 * - Works only in single threaded env (Not thread safe)
 */
class Singleton {
    // step 1: declare a private static var of type Singleton
    private static Singleton instance;

    // step 2: make the CTOR private
    private Singleton() {
        // object initialization logic
    }

    // step 3: provide a global access interface
    public static Singleton getInstance() {
        if (instance == null)
            instance = new Singleton();
        return instance;
    }
}
