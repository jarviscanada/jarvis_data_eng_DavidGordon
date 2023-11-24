package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.dao.MarketDataHttpHelper;
import ca.jrvs.apps.trading.model.IexQuote;
import ca.jrvs.apps.trading.service.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequestMapping("/quote")
public class QuoteController {
    private Logger logger = LoggerFactory.getLogger(QuoteController.class);

    @Autowired
    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        logger.debug("Creating QuoteController");
        this.quoteService = quoteService;
    }

    @GetMapping("iex/stock/{ticker}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public IexQuote getQuote(@PathVariable String ticker) {
        try {
            return quoteService.findIexQuoteByTicker(ticker);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}
