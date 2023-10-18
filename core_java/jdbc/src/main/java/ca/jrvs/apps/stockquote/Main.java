package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;

public class Main {
    public static void main(String[] args) {
        QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper("YOUR_API_KEY");

        Quote quote = quoteHttpHelper.fetchQuoteInfo("AAPL");
    }
}
