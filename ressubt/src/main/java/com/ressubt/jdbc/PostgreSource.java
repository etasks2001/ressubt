package com.ressubt.jdbc;

import java.beans.PropertyVetoException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class PostgreSource {
    private static ComboPooledDataSource connectionPool = null;

    public static ComboPooledDataSource getConnectionPool() {
	if (connectionPool == null) {
	    try {
		connectionPool = new ComboPooledDataSource();
		connectionPool.setDriverClass("org.postgresql.Driver");
		connectionPool.setJdbcUrl("jdbc:postgresql://localhost:5432/ressubt");
		connectionPool.setUser("postgres");
		connectionPool.setPassword("Mau1234");
		connectionPool.setMinPoolSize(2);
		connectionPool.setAcquireIncrement(5);
		connectionPool.setMaxPoolSize(20);
		connectionPool.setTestConnectionOnCheckout(true);
		return connectionPool;
	    } catch (PropertyVetoException e) {
		e.printStackTrace();
	    }
	}
	return null;

    }

}
