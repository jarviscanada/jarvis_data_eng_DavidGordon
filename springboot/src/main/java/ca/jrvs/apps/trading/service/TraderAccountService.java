package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Trader;
import ca.jrvs.apps.trading.model.TraderAccountView;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class TraderAccountService {
    private final Logger logger = LoggerFactory.getLogger(TraderAccountService.class);

    @Autowired
    private final TraderDao traderDao;

    @Autowired
    private final AccountDao accountDao;

    @Autowired
    private final SecurityOrderDao securityOrderDao;

    @Autowired
    private final PositionDao positionDao;

    public TraderAccountService(TraderDao traderDao, AccountDao accountDao, SecurityOrderDao securityOrderDao, PositionDao positionDao) {
        this.traderDao = traderDao;
        this.accountDao = accountDao;
        this.securityOrderDao = securityOrderDao;
        this.positionDao = positionDao;
    }

    /**
     * Create a new trader and initialize a new account with 0 amount
     * - validate user input (all fields must be non-empty)
     * - create a trader
     * - create an account
     * - create, setup, and return a new traderAccountView
     * <p>
     * Assumption: to simplify the logic, each trader has only one account where traderId == accountId
     *
     * @param trader cannot be null. All fields cannot be null except for id (auto-generated by db)
     * @return traderAccountView
     * @throws IllegalArgumentException if a trader has null fields or id is not null
     */
    public TraderAccountView createTraderAndAccount(Trader trader) {
        try {
            trader = traderDao.save(trader);

            Account account = new Account();
            account.setTraderId(trader.getId());
            accountDao.save(account);

            TraderAccountView traderAccountView = new TraderAccountView();
            traderAccountView.setTrader(trader);
            traderAccountView.setAccount(account);

            return traderAccountView;
        } catch (Exception e) {
            logger.error("Error creating Trader and Account: " + e);
        }

        throw new IllegalArgumentException("Trader was null or contained null fields");
    }

    /**
     * A trader can be deleted if and only if it has no open position and 0 cash balance
     * - validate traderId
     * - get trader account by traderId and check account balance
     * - get positions by accountId and check positions
     * - delete all securityOrders, account, trader (in this order)
     *
     * @param traderId must not be null
     * @throws IllegalArgumentException if traderId is null or not found or unable to delete
     */
    public void deleteTraderById(Integer traderId) {
        if(traderId == null) {
            throw new IllegalArgumentException("Trader id is invalid");
        }

        try {
            traderDao.deleteById(traderId);
        } catch (Exception e) {
            logger.error("Error deleting trader with id " + traderId + ": " + e.getMessage());
            throw new ResourceNotFoundException("Could not find account with id " + traderId);
        }
    }

    /**
     * Deposit a fund to an account by traderId
     * - validate user input
     * - find account by trader id
     * - update the amount accordingly
     *
     * @param traderId must not be null
     * @param fund must be greater than 0
     * @return updated Account
     * @throws IllegalArgumentException if traderId is null or not found,
     * 									and fund is less than or equal to 0
     */
    public Account deposit(Integer traderId, Double fund) {
        if(fund <= 0) {
            throw new IllegalArgumentException("Fund must be greater than 0");
        }

        if(traderId == null) {
            throw new IllegalArgumentException("Trader id is invalid");
        }

        try {
            Account account = accountDao.getOne(traderId);
            account.setAmount(fund);
            return accountDao.save(account);
        } catch (Exception e) {
            logger.error("Error depositing into account " + traderId + ": " + e.getMessage());
        }

        throw new ResourceNotFoundException("Could not find account with id " + traderId);
    }

    /**
     * Withdraw a fund to an account by traderId
     * - validate user input
     * - find account by trader id
     * - update the amount accordingly
     *
     * @param traderId must not be null
     * @param fund must be greater than 0
     * @return updated Account
     * @throws IllegalArgumentException if traderId is null or not found,
     * 									and fund is less than or equal to 0
     */
    public Account withdraw(Integer traderId, Double fund) {
        if(fund <= 0) {
            throw new IllegalArgumentException("Fund must be greater than 0");
        }

        if(traderId == null) {
            throw new IllegalArgumentException("Trader id is invalid");
        }

        try {
            Account account = accountDao.getOne(traderId);
            account.setAmount(account.getAmount() - fund);
            return accountDao.save(account);
        } catch (Exception e) {
            logger.error("Error depositing into account " + traderId + ": " + e.getMessage());
        }

        throw new ResourceNotFoundException("Could not find account with id " + traderId);
    }
}