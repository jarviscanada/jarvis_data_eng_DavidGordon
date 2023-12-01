package ca.jrvs.apps.trading.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Position {
    @Id
    private int accountId;
    private String ticker;
    private long position;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }
}
