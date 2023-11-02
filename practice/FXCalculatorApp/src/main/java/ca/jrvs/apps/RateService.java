package ca.jrvs.apps;

import java.sql.Date;

public class RateService {

    private DailyRateRepo repo;
    private RateHttpHelper http;

    public RateService(DailyRateRepo repo, RateHttpHelper http) {
        this.repo = repo;
        this.http = http;
    }

    public double calculateExchange(String fromSymbol, String toSymbol, Date date, double amount) {
        return 0.0;
    }

}
