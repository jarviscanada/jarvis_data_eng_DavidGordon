package ca.jrvs.apps.stockquote.dao;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
public class QuoteHttpHelperTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void fetchQuoteInfoTest() {
        // Arrange
        String apiKey = "";
        String[] properties = new String[7];
        try {
            File propertiesFile = Paths.get(Objects.requireNonNull(
                    getClass().getClassLoader().getResource("properties.txt")).toURI()).toFile();

            Reader reader = new FileReader(propertiesFile.getAbsolutePath());
            BufferedReader bufferedReader = new BufferedReader(reader);
            int i = 0;
            while(i < 7) {
                properties[i] = bufferedReader.readLine().split(":")[1];
                i++;
            }
            apiKey = properties[6];
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper(apiKey);
        String expectedSymbol = "AAPL";

        // Act
        Quote resultQuote = quoteHttpHelper.fetchQuoteInfo("AAPL");

        // Assert
        assertEquals(expectedSymbol, resultQuote.getSymbol());
    }
}