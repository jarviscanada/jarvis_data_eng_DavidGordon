package ca.jrvs.apps.stockquote.dao;

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
        QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper("YOUR_API_KEY");
        String expectedSymbol = "AAPL";

        // Act
        Quote resultQuote = quoteHttpHelper.fetchQuoteInfo("AAPL");

        // Assert
        assertEquals(expectedSymbol, resultQuote.getSymbol());
    }
}