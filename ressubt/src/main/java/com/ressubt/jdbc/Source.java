package com.ressubt.jdbc;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public interface Source {

    public ComboPooledDataSource getConnection() throws Exception;
}
