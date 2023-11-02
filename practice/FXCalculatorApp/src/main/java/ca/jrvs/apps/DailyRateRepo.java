package ca.jrvs.apps;

import java.sql.Connection;
import java.sql.Date;
import java.util.Optional;

public class DailyRateRepo {

    private Connection c;

    public DailyRateRepo(Connection c) {
        this.c = c;
    }

    public Optional<Rate> getRate(Date date, String fromCode, String toCode) {
        return null;
    }

    public void save(Rate r) {
        //TO DO
    }

}