package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.util.PropertiesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

public class DatabaseConnectionManager {
    final Logger logger = LoggerFactory.getLogger(DatabaseConnectionManager.class);
    private final String connectionString;
    private final Properties properties;

    public DatabaseConnectionManager() {
        String[] properties  = PropertiesHelper.getProperties();

        this.connectionString = "jdbc:postgresql://"+properties[0]+"/"+properties[1];
        this.properties = new Properties();
        this.properties.setProperty("user", properties[2]);
        this.properties.setProperty("password", properties[3]);
    }

    public Connection getConnection() throws SQLException {
        logger.info(new Timestamp(new Date().getTime()) + ": New database connection");
        return DriverManager.getConnection(this.connectionString, this.properties);
    }
}
