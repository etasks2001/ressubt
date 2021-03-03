package com.ressubt.listener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.PooledDataSource;

@WebListener
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
	System.out.println("Destroing: " + sce.getServletContext().getContextPath());

	for (Object o : C3P0Registry.getPooledDataSources()) {
	    try {
		((PooledDataSource) o).close();
	    } catch (Exception e) {

	    }
	}

	// https://stackoverflow.com/questions/3320400/to-prevent-a-memory-leak-the-jdbc-driver-has-been-forcibly-unregistered/23912257#23912257
	// ... First close any background tasks which may be using the DB ...
	// ... Then close any DB connection pools ...

	// Now deregister JDBC drivers in this context's ClassLoader:
	// Get the webapp's ClassLoader
	ClassLoader cl = Thread.currentThread().getContextClassLoader();
	// Loop through all drivers
	Enumeration<Driver> drivers = DriverManager.getDrivers();
	while (drivers.hasMoreElements()) {
	    Driver driver = drivers.nextElement();
	    if (driver.getClass().getClassLoader() == cl) {
		// This driver was registered by the webapp's ClassLoader, so deregister it:
		try {
		    System.out.println("Deregistering JDBC driver {}" + driver);
		    DriverManager.deregisterDriver(driver);
		} catch (SQLException ex) {
		    System.out.println("Error deregistering JDBC driver {}" + driver);
		}
	    } else {
		// driver was not registered by the webapp's ClassLoader and may be in use
		// elsewhere
		System.out.println("Not deregistering JDBC driver {} as it does not belong to this webapp's ClassLoader" + driver);
	    }
	}

	System.out.println("closed");
    }
}
