package ca.jrvs.apps.trading.model;

public class TraderAccountView {
    private Trader trader;
    private Account account;

    public void setTrader(Trader trader) {
        this.trader = trader;
    }

    public Trader getTrader() {
        return trader;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
