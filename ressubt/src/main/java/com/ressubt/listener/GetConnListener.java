package com.ressubt.listener;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;

@WebListener
public class GetConnListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
	try {

	    ComboPooledDataSource dataSource = new ComboPooledDataSource();
	    dataSource.setDriverClass("org.postgresql.Driver");
	    dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/ressubt");
	    dataSource.setUser("postgres");
	    dataSource.setPassword("casalafite2013");
	    dataSource.setCheckoutTimeout(30000);
	    dataSource.setTestConnectionOnCheckout(true);
	    dataSource.setMaxIdleTime(30);

	    dataSource.setMinPoolSize(15);
	    dataSource.setInitialPoolSize(16);
	    dataSource.setMaxPoolSize(200);

	    dataSource.setAcquireIncrement(1);
	    dataSource.setAcquireRetryDelay(1000);
	    dataSource.setAcquireRetryAttempts(5);
//	    dataSource.setNumHelperThreads(4);

//	    dataSource.setMaxStatements(300);
//	    dataSource.setMaxStatementsPerConnection(300);

	    dataSource.setIdleConnectionTestPeriod(30);

	    ServletContext servletContext = sce.getServletContext();

	    servletContext.setAttribute("dataSource", dataSource);

	    System.out.println("Data Source is configured!");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
	try {
	    ServletContext application = sce.getServletContext();
	    ComboPooledDataSource ds = (ComboPooledDataSource) application.getAttribute("dataSource");
	    try {
		Connection conn = ds.getConnection();
		if (conn != null) {
		    conn.close();
		    Thread.sleep(1000);
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	    Set pooledDataSources = C3P0Registry.getPooledDataSources();
	    System.out.println("Pool size: " + pooledDataSources.size());
	    for (Object o : pooledDataSources) {
		((PooledDataSource) o).close();
		System.out.println(o);
		Thread.sleep(1000);
	    }

	    // https://stackoverflow.com/questions/3320400/to-prevent-a-memory-leak-the-jdbc-driver-has-been-forcibly-unregistered/23912257#23912257
	    ClassLoader cl = Thread.currentThread().getContextClassLoader();
	    Enumeration<Driver> drivers = DriverManager.getDrivers();
	    while (drivers.hasMoreElements()) {
		Driver driver = drivers.nextElement();
		if (driver.getClass().getClassLoader() == cl) {
		    try {
			System.out.println("Deregistering JDBC driver {}" + driver);
			DriverManager.deregisterDriver(driver);
			Thread.sleep(1000);
		    } catch (SQLException ex) {
			System.out.println("Error deregistering JDBC driver {}" + driver);
		    }
		} else {
		    System.out.println("Not deregistering JDBC driver {} as it does not belong to this webapp's ClassLoader" + driver);
		}
	    }

	    System.out.println("Tomcat closed");
	} catch (Exception e) {

	}

    }
}