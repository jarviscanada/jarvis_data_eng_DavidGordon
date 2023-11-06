package ca.jrvs.apps.jdbc;

public class Main {
    public static void main(String[] args) {
        JDBCExecutor executor = new JDBCExecutor();
        Customer customer = new Customer();

        // Create a Customer
        customer.setId(10001);
        customer.setFirstName("David");
        customer.setLastName("Gordon");
        customer.setEmail("davidgordondev@gmail.com");
        customer.setPhone("2266065657");
        customer.setAddress("40 King Street West");
        customer.setCity("Toronto");
        customer.setState("Ontario");
        customer.setZipcode("N1P1G5");

        System.out.println(executor.createCustomer(customer).toString());

        // Find a Customer
        System.out.println(executor.findCustomerById(10001));

        // Update a Customer
        customer.setId(10001);
        customer.setFirstName("David");
        customer.setLastName("Gordon");
        customer.setEmail("davidgordondev@gmail.com");
        customer.setPhone("2266065657");
        customer.setAddress("40 King Street West");
        customer.setCity("Toronto");
        customer.setState("Ontario");
        customer.setZipcode("N1P1G5");

        System.out.println(executor.updateCustomer(customer).toString());

        /* Find all Customers
        for(Customer c : executor.findAllCustomers()) {
            System.out.println(c.toString());
        }
        */
    }
}
