package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor {
    public void createCustomer() {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport",
                "postgres", "password");

        try {
            Connection connection = dcm.getConnection();
            CustomerDAO customerDAO = new CustomerDAO(connection);

            Customer customer = new Customer();
            customer.setFirstName("David");
            customer.setLastName("Gordon");
            customer.setEmail("davidgordondev@gmail.com");
            customer.setPhone("2266065657");
            customer.setAddress("36 Bailey Dr");
            customer.setCity("Kitchener");
            customer.setState("Ontario");
            customer.setZipcode("N1P1G5");

            customerDAO.create(customer);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
