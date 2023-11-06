package ca.jrvs.apps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;

public class FxController {
    private static final Logger logger = LoggerFactory.getLogger(FxController.class);
    private RateService rateService;
    private CurrencyService currService;

    public FxController(RateService service, CurrencyService currService) {
        this.rateService = service;
        this.currService = currService;
    }

    public void processExchange(String fromCode, String toCode, String date, String amount) {
        if(!currService.isValidCode(fromCode) || !currService.isValidCode(toCode)) {
            logger.error("Invalid codes provided in FxController");
            return;
        }

        // the date needs to be and amount also need to be validated
        // todo

        // then we need to calculate the exchange with our codes
        double exchange = rateService.calculateExchange(fromCode, toCode, Date.valueOf(date), Double.parseDouble(amount));

        // we should check if exchange is valid here before printing
        System.out.println("Your exchange is " + exchange);
    }

}
