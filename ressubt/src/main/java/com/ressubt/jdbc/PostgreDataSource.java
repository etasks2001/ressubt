package com.ressubt.jdbc;

import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;

class PostgreDataSource {
    private ComboPooledDataSource connectionPool = new ComboPooledDataSource("ressubt");

    public ComboPooledDataSource getConnectionPool() {

	PooledDataSource pooledDataSourceByName = C3P0Registry.pooledDataSourceByName("ressubt");

	System.out.println(C3P0Registry.getPooledDataSources().size());

	return (ComboPooledDataSource) pooledDataSourceByName;
    }

}