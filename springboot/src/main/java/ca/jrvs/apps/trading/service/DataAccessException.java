package ca.jrvs.apps.trading.service;

public class DataAccessException extends RuntimeException {
    public DataAccessException(String message) {
        super(message);
    }
}