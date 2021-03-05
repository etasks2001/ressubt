package com.ressubt.listener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@WebListener
public class ApplicationServletContextListener implements ServletContextListener {

    private static final Logger LOG = LogManager.getLogger(ApplicationServletContextListener.class);

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

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
	try {
	    ServletContext applicationContext = sce.getServletContext();
	    ComboPooledDataSource dataSource = (ComboPooledDataSource) applicationContext.getAttribute("dataSource");
	    dataSource.close();
	    LOG.info(dataSource);
	    Thread.sleep(1000);

	    // https://stackoverflow.com/questions/3320400/to-prevent-a-memory-leak-the-jdbc-driver-has-been-forcibly-unregistered/23912257#23912257

	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    Enumeration<Driver> drivers = DriverManager.getDrivers();

	    while (drivers.hasMoreElements()) {
		Driver driver = drivers.nextElement();

		if (driver.getClass().getClassLoader() == classLoader) {
		    LOG.info("Deregistering JDBC driver " + driver.getClass().getName());
		    DriverManager.deregisterDriver(driver);
		    Thread.sleep(1000);
		} else {
		    LOG.info("Not deregistering JDBC driver {} as it does not belong to this webapp's ClassLoader " + driver);
		}
	    }
	    LOG.info("Tomcat closed");

	} catch (ExceptionInInitializerError | SQLException | InterruptedException e) {
	    LOG.error(e);
	}
    }
}