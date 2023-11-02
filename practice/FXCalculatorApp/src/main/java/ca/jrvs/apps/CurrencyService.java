package ca.jrvs.apps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CurrencyService {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyService.class);
    private CurrencyRepo repo;

    public CurrencyService(CurrencyRepo repo) {
        logger.info("Initializing CurrencyService");
        this.repo = repo;
    }

    public boolean isValidCode(String code) {
        logger.info("Validating currency code");
        return repo.getCurrency(code).isPresent();
    }

}
