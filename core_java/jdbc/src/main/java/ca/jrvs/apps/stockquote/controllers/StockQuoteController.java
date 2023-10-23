package ca.jrvs.apps.stockquote.controllers;

import ca.jrvs.apps.stockquote.dao.Position;
import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.services.PositionService;
import ca.jrvs.apps.stockquote.services.QuoteService;

import java.sql.Connection;
import java.util.*;

public class StockQuoteController {
    private QuoteService quoteService;
    private PositionService positionService;

    /**
     * User interface for our application
     */
    public void initClient(Connection connection, String apiKey) {
        List<Position> positions = new ArrayList<Position>();

        quoteService = new QuoteService(connection, apiKey);
        positionService = new PositionService(connection);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu Options: ");
            System.out.println("1: Buy");
            System.out.println("2: Sell");
            System.out.println("3: View stock info");
            System.out.println("4: View your positions");
            System.out.println("5: Exit\n");
            System.out.print("> ");
            int input = scanner.nextInt();

            if(input == 1) {
                System.out.println("Please enter a stock symbol");
                String symbol = scanner.next();
                System.out.println("Please enter an amount");
                int amount = scanner.nextInt();
                System.out.println("Please enter a price");
                double price = scanner.nextDouble();

                Position position = positionService.buy(symbol, amount, price);
                positions.add(position);
            }

            if(input == 2) {
                System.out.println("Please enter a stock symbol.");
                String symbol = scanner.next();
                positions.stream().filter(p -> Objects.equals(p.getSymbol(), symbol)).forEach(positions::remove);
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
                positions.forEach(p -> System.out.println(p.toString()));
            }

            if(input == 5 || !Character.isDigit(input)) {
                break;
            }
        }

        scanner.close();
    }
}
