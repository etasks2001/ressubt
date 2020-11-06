package com.ressubt.jdbc;

import java.sql.Connection;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

public class PostgreSourceTest {

    @Test
    public void open_connection() throws Exception {
	Connection connection = PostgreDataSource.getConnectionPool().getConnection();

	MatcherAssert.assertThat(connection.isClosed(), Matchers.equalTo(false));

	connection.close();
	MatcherAssert.assertThat(connection.isClosed(), Matchers.equalTo(true));

    }

}
