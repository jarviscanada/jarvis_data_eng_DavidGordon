package ca.jrvs.apps.stockquote.dao;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.junit.MockitoTestListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.junit.Assert.*;

public class PositionDAO_UnitTest {

    PositionDAO<Position, Integer> positionDAO;
    @Mock
    DatabaseConnectionManager dcm;
    @Mock
    Connection connection;
    @Mock
    PreparedStatement statement;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void save() throws SQLException {
        // Arrange
        Mockito.when(dcm.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement("INSERT INTO position " +
                        "(symbol, number_of_shares, value_paid) VALUES (?, ?, ?)"))
                .thenReturn(statement);

        Connection c = dcm.getConnection();
        positionDAO = new PositionDAO<Position, Integer>(c);

        Position position = new Position();
        position.setSymbol("AAPL");
        position.setValuePaid(47.21);
        position.setNumOfShares(19);

        // Act
        Position savedPosition = (Position) positionDAO.save(position);

        // Assert
        assertEquals(savedPosition, position);
    }
}