package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.AppConfig;
import ca.jrvs.apps.trading.dao.IexQuoteHttpHelper;
import ca.jrvs.apps.trading.model.IexQuote;
import ca.jrvs.apps.trading.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/getQuote")
public class QuoteController {
    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("iex/stock/{ticker}")
    public IexQuote getQuote(@PathVariable String ticker) {
        return null;
    }
}
