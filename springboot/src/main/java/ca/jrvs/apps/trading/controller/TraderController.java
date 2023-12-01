package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Trader;
import ca.jrvs.apps.trading.model.TraderAccountView;
import ca.jrvs.apps.trading.service.ResourceNotFoundException;
import ca.jrvs.apps.trading.service.TraderAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/trader")
public class TraderController {
    @Autowired
    private final TraderAccountService traderAccountService;

    public TraderController(TraderAccountService traderAccountService) {
        this.traderAccountService = traderAccountService;
    }

    @PostMapping("createTrader")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TraderAccountView getTraderAccountView(@RequestBody Trader trader) {
        return traderAccountService.createTraderAndAccount(trader);
    }

    @PostMapping("deleteTrader/{traderId}")
    @ResponseStatus(HttpStatus.OK)
    public void getTraderAccountView(@RequestParam Integer traderId) {
        traderAccountService.deleteTraderById(traderId);
    }

    @PostMapping("/deposit/traderId/{traderId}/amount/{amount}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Account depositFund(Integer traderId, Double amount) {
        return traderAccountService.deposit(traderId, amount);
    }

    @PostMapping("/withdraw/traderId/{traderId}/amount/{amount}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Account withdrawFund(Integer traderId, Double amount) {
        return traderAccountService.withdraw(traderId, amount);
    }
}
