package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.MarketDataHttpHelper;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.MarketOrder;
import ca.jrvs.apps.trading.model.SecurityOrder;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {TestConfig.class})
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Test
    void executeMarketOrderTest() {
        // Arrange
        MarketOrder marketOrder = new MarketOrder();
        marketOrder.setTicker("aapl");
        marketOrder.setSize(13);
        marketOrder.setOption("buy");
        marketOrder.setTraderId(1);

        // Act
        SecurityOrder savedOrder = orderService.executeMarketOrder(marketOrder);

        // Assert
        assertNotNull(savedOrder);
        assertEquals(savedOrder.getTicker(), marketOrder.getTicker());
    }
}
