package ca.jrvs.apps.stockquote.services;

import ca.jrvs.apps.stockquote.dao.DatabaseConnectionManager;
import ca.jrvs.apps.stockquote.dao.Position;
import ca.jrvs.apps.stockquote.dao.PositionDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class PositionService {
    private Connection connection;
    private PositionDAO positionDAO;

    public PositionService() {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager();

        try {
            connection = dcm.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        positionDAO = new PositionDAO<Position, Integer>(connection);
    }

    public Position buy(String symbol, int numberOfShares, double price) {
        return null;
    }

    public void sell(int id) {

    }
}
