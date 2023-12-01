package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Trader;
import ca.jrvs.apps.trading.model.TraderAccountView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {TestConfig.class})
class TraderAccountServiceTest {
    @Autowired
    private TraderAccountService traderAccountService;

    @Test
    void createTraderAndAccountTest() {
        // Arrange
        Trader trader = new Trader();
        trader.setFirstName("David");
        trader.setLastName("Gordon");
        trader.setDob(new Date(2002-12-13));
        trader.setCountry("Canada");
        trader.setEmail("davidgordondev@gmail.com");

        // Act
        TraderAccountView savedTraderAccountView = traderAccountService.createTraderAndAccount(trader);

        assertEquals(trader, savedTraderAccountView.getTrader());
        assertEquals(savedTraderAccountView.getTrader().getId(), savedTraderAccountView.getAccount().getId());
    }
}