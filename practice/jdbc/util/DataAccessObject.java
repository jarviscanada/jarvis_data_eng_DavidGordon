package ca.jrvs.apps.jdbc.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class DataAccessObject <T extends DataTransferObject> {
    protected final Connection connection;
    protected final static String LAST_VAL = "SELECT last_value FROM";
    protected final static String CUSTOMER_SEQUENCE = "hp_customer_seq";

    public DataAccessObject(Connection connection) {
        this.connection = connection;
    }

    public abstract T findById(long id);
    public abstract List<T> findAll();
    public abstract T findByEmail(String email);
    public abstract T create(T dto);
    public abstract T update(T dto);
    public abstract void delete(long id);

    protected int getLastVal() {
        int key = 0;
        String sql = LAST_VAL + DataAccessObject.CUSTOMER_SEQUENCE;

        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()) {
                key = resultSet.getInt(1);
            }
            return key;
        } catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}