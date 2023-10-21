package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.util.PropertiesHelper;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class DatabaseConnectionManager {
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
        return DriverManager.getConnection(this.connectionString, this.properties);
    }
}
