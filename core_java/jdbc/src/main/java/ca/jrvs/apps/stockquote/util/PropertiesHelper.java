package ca.jrvs.apps.stockquote.util;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;

public class PropertiesHelper {
    public static String[] getProperties() {
        String[] properties = new String[7];

        try {
            File propertiesFile = Paths.get(Objects.requireNonNull(
                    PropertiesHelper.class.getClassLoader().getResource("properties.txt")).toURI()).toFile();

            Reader reader = new FileReader(propertiesFile.getAbsolutePath());
            BufferedReader bufferedReader = new BufferedReader(reader);

            int i = 0;
            while(i < 7) {
                // Properties format = key:value, so we split at the semicolon and take the 2nd value
                properties[i] = bufferedReader.readLine().split(":")[1];
                i++;
            }

            return properties;
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
