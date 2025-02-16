package com.assignment.question;

import java.util.concurrent.ConcurrentHashMap;

/*
Implementation v1: FIXED - You are responsible for initializing the connection pool

[INFO] Results:
[INFO] 
[ERROR] Failures: 
[ERROR]   ConnectionPoolTest.testAvailableConnectionsCount:164 The available connections count should decrease after obtaining connections ==> expected: <-2> but was: <0>
[ERROR]   ConnectionPoolTest.testGetConnection:125 A valid connection should be returned ==> expected: not <null>
[ERROR]   ConnectionPoolTest.testReleaseConnection:141 A valid connection should be returned ==> expected: not <null>
[INFO] 
[ERROR] Tests run: 7, Failures: 3, Errors: 0, Skipped: 0

*/

public class ConnectionPoolImpl implements ConnectionPool {
    // part 1: Implement Singleton - Double Checked Lock (DCL)
    // step 1: declare private static var of type ConnectionPoolImpl
    private static volatile ConnectionPoolImpl instance;
    private Integer maxConnections;
    // Thread safe collection
    private ConcurrentHashMap<DatabaseConnection, Boolean> availableDBConnections;
    private ConcurrentHashMap<DatabaseConnection, Boolean> allocatedDBConnections;

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
        this.availableDBConnections = new ConcurrentHashMap<>();
        for (int i = 0; i < this.maxConnections; i++)
            this.availableDBConnections.put(new DatabaseConnection(), true);
    }

    @Override
    public DatabaseConnection getConnection() {
        if (this.availableDBConnections == null || this.availableDBConnections.size() == 0)
            return null;
        DatabaseConnection dbConnection = this.availableDBConnections.keys().nextElement();
        this.availableDBConnections.remove(dbConnection);
        if (this.allocatedDBConnections == null)
            this.allocatedDBConnections = new ConcurrentHashMap<>();
        this.allocatedDBConnections.put(dbConnection, false);
        return dbConnection;
    }

    @Override
    public void releaseConnection(DatabaseConnection connection) {
        if (this.allocatedDBConnections != null && this.allocatedDBConnections.size() > 0) {
            this.allocatedDBConnections.remove(connection);
            this.availableDBConnections.put(connection, true);
        }
    }

    @Override
    public int getAvailableConnectionsCount() {
        if (this.availableDBConnections != null)
            return this.availableDBConnections.size();
        return 0;
    }

    @Override
    public int getTotalConnectionsCount() {
        return this.maxConnections;
    }
}