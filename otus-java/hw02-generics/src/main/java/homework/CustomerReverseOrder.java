package homework;


import java.util.LinkedList;

public class CustomerReverseOrder {

    private final LinkedList<Customer> storage = new LinkedList<>();

    public void add(Customer customer) {
        storage.add(customer);
    }

    public Customer take() {
        return storage.pollLast();
    }
}