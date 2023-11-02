package ca.jrvs.apps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.Optional;

public class RateService {
    private static final Logger logger = LoggerFactory.getLogger(RateService.class);
    private final DailyRateRepo repo;
    private final RateHttpHelper http;

    public RateService(DailyRateRepo repo, RateHttpHelper http) {
        logger.info("Initializing RateService");
        this.repo = repo;
        this.http = http;
    }

    public double calculateExchange(String fromSymbol, String toSymbol, Date date, double amount) {
        logger.info("Calculating exchange");

        Optional<Rate> databaseRate = repo.getRate(date, fromSymbol, toSymbol);
        if(databaseRate.isPresent()) {
            Rate rate = databaseRate.get();
            return rate.getClose() * amount;
        }

        Optional<Rate> apiRate = http.fetchRate(fromSymbol, toSymbol, String.valueOf(date));
        if(apiRate.isPresent()) {
            // If the database didn't return a rate, we need to save a new one from the API
            Rate rate = apiRate.get();
            return rate.getClose() * amount;
        }

        logger.error("Couldn't fetch rate from database or api");
        throw new RuntimeException();
    }

}
