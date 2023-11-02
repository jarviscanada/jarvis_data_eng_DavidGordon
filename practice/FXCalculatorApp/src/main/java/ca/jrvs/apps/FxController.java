package ca.jrvs.apps;

public class FxController {

    private RateService rateService;
    private CurrencyService currService;

    public FxController(RateService service, CurrencyService currService) {
        this.rateService = service;
        this.currService = currService;
    }

    public void processExchange(String fromCode, String toCode, String date, String amount) {
        //TO DO
    }

}
