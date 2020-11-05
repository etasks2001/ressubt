package com.ressubt.jdbc;

import java.sql.Connection;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

public class PostgreSourceTest {

    @Test
    public void open_connection() throws Exception {
	Connection connection = PostgreSource.getConnectionPool().getConnection();

	MatcherAssert.assertThat(connection.isClosed(), Matchers.equalTo(false));

    }

}
