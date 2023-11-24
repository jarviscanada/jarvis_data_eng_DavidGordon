package ca.jrvs.apps.trading.dao;

import javax.persistence.Entity;

@Entity
public class Quote {
    private String ticker;
    private double last_price;
    private double bid_price;
    private int bid_size;
    private double ask_price;
    private int ask_size;
}
