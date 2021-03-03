package com.ressubt.jdbc;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class PostgreDataSource {
    private static ComboPooledDataSource connectionPool = new ComboPooledDataSource();

    public static ComboPooledDataSource getConnectionPool() {

	return connectionPool;
    }

}