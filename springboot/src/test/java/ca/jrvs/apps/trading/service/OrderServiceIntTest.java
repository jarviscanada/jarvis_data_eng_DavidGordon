package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.MarketDataHttpHelper;
import ca.jrvs.apps.trading.model.*;
import org.junit.Before;
import org.junit.internal.runners.statements.Fail;
import org.junit.jupiter.api.Test;
import org.junit.runner.notification.Failure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {TestConfig.class})
public class OrderServiceIntTest {
    @Autowired
    MarketDataHttpHelper marketDataHttpHelper;

    @Autowired
    QuoteService quoteService;

    @Autowired
    TraderAccountService traderAccountService;

    @Autowired
    OrderService orderService;

    @Test
    void fetchAndPersistMarketDataTest() {
        Optional<IexQuote> iexQuote = marketDataHttpHelper.getIexQuote("MSFT");
        if(iexQuote.isEmpty()) fail("IexQuote was empty");

        Quote quote = quoteService.buildQuoteFromIexQuote(iexQuote.get());
        Quote savedQuote = quoteService.saveQuote(quote);

        assertEquals(quote, savedQuote);

        Trader trader = new Trader();
        trader.setFirstName("Bob");
        trader.setLastName("Joe");
        trader.setDob(new Date(1999,8,24));
        trader.setCountry("Canada");
        trader.setEmail("bobjoe@jrvs.ca");
        TraderAccountView traderAccountView = traderAccountService.createTraderAndAccount(trader);
        Trader savedTrader = traderAccountView.getTrader();

        assertEquals(trader, savedTrader);

        MarketOrder marketOrder = new MarketOrder();
        marketOrder.setTicker("MSFT");
        marketOrder.setSize(13);
        marketOrder.setOption("buy");
        marketOrder.setTraderId(trader.getId());

        // Act
        SecurityOrder savedOrder = orderService.executeMarketOrder(marketOrder);

        // Assert
        assertNotNull(savedOrder);
        assertEquals(savedOrder.getTicker(), marketOrder.getTicker());
    }
}
