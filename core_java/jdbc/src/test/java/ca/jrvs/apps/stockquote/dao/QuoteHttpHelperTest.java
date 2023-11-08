package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.util.PropertiesHelper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class QuoteHttpHelperTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void fetchQuoteInfoTest() {
        // Arrange
        String[] properties  = PropertiesHelper.getProperties();
        String apiKey = properties[6];
        QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper(apiKey);
        String expectedSymbol = "AAPL";

        // Act
        Quote resultQuote = quoteHttpHelper.fetchQuoteInfo("AAPL");

        // Assert
        assertEquals(expectedSymbol, resultQuote.getSymbol());
    }
}