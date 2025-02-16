package com.example.singleton;

/**
 * Design Pattern: Singleton
 * - Type: Creational
 * 
 * Implementation: v2
 * - Eager Initialization
 * 
 * Pros:
 * - Simple
 * - Thread Safe
 * 
 * Cons:
 * - The object is initialized even when it is not needed
 * (The object is initialized as soon as the class is loaded)
 */
class Singleton {
    // step 1: initialize a private static var of type Singleton
    private static final Singleton instance = new Singleton();

    // step 2: make the CTOR private
    private Singleton() {
        // object initialization logic
    }

    // step 3: provide a global access interface
    public static Singleton getInstance() {
        return instance;
    }
}
