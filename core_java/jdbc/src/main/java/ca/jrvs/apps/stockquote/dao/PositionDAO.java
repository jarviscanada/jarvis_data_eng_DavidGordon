package ca.jrvs.apps.stockquote.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PositionDAO<Position, Integer> implements CrudDao<Position, Integer> {
    protected final Connection connection;
    private static final String SAVE = "INSERT INTO position " +
            "(symbol, number_of_shares, value_paid) " +
            "VALUES (?, ?, ?)";

    private static final String FIND_BY_ID = "SELECT * FROM position WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM position";
    private static final String DELETE = "DELETE FROM position WHERE id = ?";
    private static final String DELETE_ALL = "TRUNCATE TABLE position";
    public PositionDAO(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Position save(Position entity) throws IllegalArgumentException {
        if(entity == null) {
            throw new IllegalArgumentException();
        }

        ca.jrvs.apps.stockquote.dao.Position position = (ca.jrvs.apps.stockquote.dao.Position) entity;

        try (PreparedStatement statement = this.connection.prepareStatement(SAVE)) {
            statement.setString(1, position.getSymbol());
            statement.setInt(2, position.getNumOfShares());
            statement.setDouble(3, position.getValuePaid());
            statement.execute();

            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Position> findById(Integer integer) throws IllegalArgumentException {
        if(integer == null) {
            throw new IllegalArgumentException();
        }

        ca.jrvs.apps.stockquote.dao.Position position = new ca.jrvs.apps.stockquote.dao.Position();

        try (PreparedStatement statement = this.connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, (java.lang.Integer) integer);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                position.setId(resultSet.getInt(1));
                position.setSymbol(resultSet.getString(2));
                position.setNumOfShares(resultSet.getInt(3));
                position.setValuePaid(resultSet.getDouble(4));
            }

            return Optional.of((Position)position);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Position> findAll() {
        List<Position> positions = new ArrayList<>();
        try (PreparedStatement statement = this.connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ca.jrvs.apps.stockquote.dao.Position position = new ca.jrvs.apps.stockquote.dao.Position();
                position.setId(resultSet.getInt(1));
                position.setSymbol(resultSet.getString(2));
                position.setNumOfShares(resultSet.getInt(3));
                position.setValuePaid(resultSet.getDouble(4));
                positions.add((Position) position);
            }
            return positions;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer integer) throws IllegalArgumentException {
            if(integer == null) {
                throw new IllegalArgumentException();
            }

            try (PreparedStatement statement = this.connection.prepareStatement(DELETE)) {
                statement.setInt(1, (int)integer);
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
    }

    @Override
    public void deleteAll() {
        try (PreparedStatement statement = this.connection.prepareStatement(DELETE_ALL)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
