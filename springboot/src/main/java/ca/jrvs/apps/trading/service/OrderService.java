package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.MarketOrder;
import ca.jrvs.apps.trading.model.SecurityOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private AccountDao accountDao;

    @Autowired
    private SecurityOrderDao securityOrderDao;


    public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao) {
        this.accountDao = accountDao;
        this.securityOrderDao = securityOrderDao;
    }

    /**
     * Execute a market order
     * - validate the order (e.g. size and ticker)
     * - create a securityOrder
     * - handle buy or sell orders
     * 	- buy order : check account balance
     * 	- sell order : check position for the ticker/symbol
     * 	- do not forget to update the securityOrder.status
     * - save and return securityOrder
     *
     * NOTE: you are encouraged to make some helper methods (protected or private)
     *
     * @param marketOrder market order
     * @return SecurityOrder from security_order table
     * @throws DataAccessException if unable to get data from DAO
     * @throws IllegalArgumentException for invalid inputs
     */
    public SecurityOrder executeMarketOrder(MarketOrder marketOrder) {
        Account account = accountDao.getOne(marketOrder.getTraderId());
        Optional<SecurityOrder> securityOrderOptional = securityOrderDao.findAll().stream().dropWhile(x -> !Objects.equals(x.getTicker(), marketOrder.getTicker())).findFirst();

        if(securityOrderOptional.isEmpty()) {
            throw new IllegalArgumentException("Could not find security order with ticker " + marketOrder.getTicker());
        }

        SecurityOrder securityOrder = securityOrderOptional.get();

        if(marketOrder.getOption().equalsIgnoreCase("buy")) {
            handleBuyMarketOrder(marketOrder, securityOrder, account);
        } else {
            handleBuyMarketOrder(marketOrder, securityOrder, account);
        }

        return securityOrder;
    }

    /**
     * Helper method to execute a buy order
     *
     * @param marketOrder user order
     * @param securityOrder to be saved in database
     * @param account account
     */
    protected void handleBuyMarketOrder(MarketOrder marketOrder, SecurityOrder securityOrder, Account account) {
        securityOrder.setTicker(marketOrder.getTicker());
        securityOrder.setSize(marketOrder.getSize());
        securityOrder.setAccountId(account.getTraderId());

        securityOrderDao.save(securityOrder);

        throw new DataAccessException("Unable to retrieve data from database");
    }

    /**
     * Helper method to execute a sell order
     *
     * @param marketOrder user order
     * @param securityOrder to be saved in database
     * @param account account
     */
    protected void handleSellMarketOrder(MarketOrder marketOrder, SecurityOrder securityOrder, Account account) {
        securityOrder.setTicker(marketOrder.getTicker());
        securityOrder.setSize(marketOrder.getSize());
        securityOrder.setAccountId(account.getTraderId());

        securityOrderDao.save(securityOrder);

        throw new DataAccessException("Unable to retrieve data from database");
    }
}
