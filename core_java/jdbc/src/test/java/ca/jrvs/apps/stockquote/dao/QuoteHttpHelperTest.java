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
        QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper("92f1efc4f2msh7f3d1b2d831b3c6p16dd4djsn6d12127ac48c");
        String expectedSymbol = "AAPL";

        // Act
        Quote resultQuote = quoteHttpHelper.fetchQuoteInfo("AAPL");

        // Assert
        assertEquals(expectedSymbol, resultQuote.getSymbol());
    }
}