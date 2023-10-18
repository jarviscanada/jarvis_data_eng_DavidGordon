package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;

public class Main {
    public static void main(String[] args) {
        QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper("92f1efc4f2msh7f3d1b2d831b3c6p16dd4djsn6d12127ac48c");

        Quote quote = quoteHttpHelper.fetchQuoteInfo("AAPL");
    }
}
