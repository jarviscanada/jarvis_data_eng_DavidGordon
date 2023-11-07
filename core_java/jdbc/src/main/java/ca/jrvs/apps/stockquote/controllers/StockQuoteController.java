package ca.jrvs.apps.stockquote.controllers;

import ca.jrvs.apps.stockquote.dao.DatabaseConnectionManager;
import ca.jrvs.apps.stockquote.dao.Position;
import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.services.PositionService;
import ca.jrvs.apps.stockquote.services.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.*;

public class StockQuoteController {
    final Logger logger = LoggerFactory.getLogger(StockQuoteController.class);
    private QuoteService quoteService;
    private PositionService positionService;

    /**
     * User interface for our application
     */
    public void initClient(Connection connection, String apiKey) {
        logger.info(new Timestamp(new Date().getTime()) + ": Initiating client");
        quoteService = new QuoteService(connection, apiKey);
        positionService = new PositionService(connection, quoteService);

        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        do {
            System.out.println("Menu Options: ");
            System.out.println("1: Buy");
            System.out.println("2: Sell");
            System.out.println("3: View stock info");
            System.out.println("4: Exit\n");
            System.out.print("> ");
            int input = scanner.nextInt();

            if(input == 1) {
                System.out.println("Please enter a stock symbol");
                String symbol = scanner.next().toUpperCase();

                Optional<Quote> quote = quoteService.fetchQuoteDataFromAPI(symbol);

                if(quote.isEmpty() || quote.get().getSymbol() == null) {
                    System.out.println("Could not find quote with symbol " + symbol);
                    continue;
                }

                System.out.println("Please enter an amount");
                int amount = scanner.nextInt();
                double price = quote.get().getPrice();
                System.out.println("Current price per share for " + symbol + " is " + price);
                System.out.println("Your total is " + price * amount);
                positionService.buy(symbol.toUpperCase(), amount, price);
            }

            if(input == 2) {
                System.out.println("Please enter an id");
                int id = scanner.nextInt();
                positionService.sell(id);
            }

            if(input == 3) {
                System.out.println("Please enter a stock symbol.");
                String symbol = scanner.next();
                Optional<Quote> quoteOptional = quoteService.fetchQuoteDataFromAPI(symbol);
                if(quoteOptional.isEmpty()) {
                    System.out.println("Couldn't find stock with symbol " + symbol + ".");
                    continue;
                } else {
                    System.out.println(quoteOptional.get().toString());
                    continue;
                }
            }

            if(input == 4) {
                loop = false;
            }
        } while (loop);

        scanner.close();
    }
}
