package ca.jrvs.apps.stockquote.services;

import ca.jrvs.apps.stockquote.dao.DatabaseConnectionManager;
import ca.jrvs.apps.stockquote.dao.Position;
import ca.jrvs.apps.stockquote.dao.PositionDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class PositionService {
    private final PositionDAO<Position, Integer> positionDAO;

    public PositionService(Connection connection) {
        positionDAO = new PositionDAO<Position, Integer>(connection);
    }

    public Position buy(String symbol, int numberOfShares, double price) {
        return null;
    }

    public void sell(int id) {

    }
}
