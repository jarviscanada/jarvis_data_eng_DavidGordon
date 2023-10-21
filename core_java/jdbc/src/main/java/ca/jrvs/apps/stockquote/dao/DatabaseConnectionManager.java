package ca.jrvs.apps.stockquote.dao;

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
        String[] properties = new String[4];

        try {
            File propertiesFile = Paths.get(Objects.requireNonNull(
                    getClass().getClassLoader().getResource("properties.txt")).toURI()).toFile();

            Reader reader = new FileReader(propertiesFile.getAbsolutePath());
            BufferedReader bufferedReader = new BufferedReader(reader);
            int i = 0;
            while(i < 4) {
                // Properties format = key:value, so we split at the semicolon and take the 2nd value
                properties[i] = bufferedReader.readLine().split(":")[1];
                i++;
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        this.connectionString = "jdbc:postgresql://"+properties[0]+"/"+properties[1];
        this.properties = new Properties();
        this.properties.setProperty("user", properties[2]);
        this.properties.setProperty("password", properties[3]);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.connectionString, this.properties);
    }
}
