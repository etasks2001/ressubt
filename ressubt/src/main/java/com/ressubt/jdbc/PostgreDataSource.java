package com.ressubt.jdbc;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class PostgreDataSource {
    private static ComboPooledDataSource connectionPool = null;

    public static ComboPooledDataSource getConnectionPool() {
	if (connectionPool == null) {
	    connectionPool = new ComboPooledDataSource();
	}
	return connectionPool;
    }

}