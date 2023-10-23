package ca.jrvs.apps.stockquote.util;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;

public class PropertiesHelper {
    public static String[] getProperties() {
        String[] properties = new String[7];

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/properties.txt"));

            int i = 0;
            while(i < 7) {
                // Properties format = key:value, so we split at the semicolon and take the 2nd value
                properties[i] = bufferedReader.readLine().split(":")[1];
                i++;
            }

            return properties;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
