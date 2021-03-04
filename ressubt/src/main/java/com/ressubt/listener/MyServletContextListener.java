package com.ressubt.listener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.PooledDataSource;

//@WebListener
public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
	System.out.println("Destroing: " + sce.getServletContext().getContextPath());

	for (Object o : C3P0Registry.getPooledDataSources()) {
	    try {
		((PooledDataSource) o).close();
		System.out.println(o);
		Thread.sleep(1000);
	    } catch (Exception e) {

	    }
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
		} catch (SQLException ex) {
		    System.out.println("Error deregistering JDBC driver {}" + driver);
		}
	    } else {
		System.out.println("Not deregistering JDBC driver {} as it does not belong to this webapp's ClassLoader" + driver);
	    }
	}

	System.out.println("Tomcat closed");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }
}
