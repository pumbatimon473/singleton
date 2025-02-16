package com.assignment.question;

import java.util.LinkedList;
import java.util.Queue;

/*
Implementation v2: Using Appropriate Data Structure - Queue (TA)
*/

public class ConnectionPoolImpl implements ConnectionPool {
    // part 1: Implement Singleton - Double Checked Lock (DCL)
    // step 1: declare private static var of type ConnectionPoolImpl
    private static volatile ConnectionPoolImpl instance;
    private Integer maxConnections;
    // Thread safe collection
    private Queue<DatabaseConnection> availableDBConnections;
    
    // step 2: make the CTOR private
    private ConnectionPoolImpl(int maxConnections) {
        this.maxConnections = maxConnections;
        this.initializePool();  // Solves the issue with the failed test cases
    }

    // step 3: provide global access interface
    public static ConnectionPoolImpl getInstance(int maxConnections) {
        if (instance == null)
            synchronized (ConnectionPoolImpl.class) {
                if (instance == null)
                    instance = new ConnectionPoolImpl(maxConnections);
            }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    @Override
    public void initializePool() {
        this.availableDBConnections = new LinkedList<>();
        for (int i = 0; i < this.maxConnections; i++)
            this.availableDBConnections.add(new DatabaseConnection());
    }

    @Override
    public DatabaseConnection getConnection() {
        return this.availableDBConnections.poll();
    }

    @Override
    public void releaseConnection(DatabaseConnection connection) {
        this.availableDBConnections.add(connection);
    }

    @Override
    public int getAvailableConnectionsCount() {
        return this.availableDBConnections.size();
    }

    @Override
    public int getTotalConnectionsCount() {
        return this.maxConnections;
    }
}