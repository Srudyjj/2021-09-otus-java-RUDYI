package homework;


import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> storage =  new TreeMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> entry = storage.firstEntry();
        if (entry == null) {
            return null;
        }
        return cloneEntry(entry);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> entry = storage.higherEntry(customer);
        if (entry == null) {
            return null;
        }
        return cloneEntry(entry);
    }

    public void add(Customer customer, String data) {
        storage.put(customer, data);
    }

    private static Map.Entry<Customer, String> cloneEntry(Map.Entry<Customer, String> entry) {
        var customer = new Customer(
                entry.getKey().getId(),
                entry.getKey().getName(),
                entry.getKey().getScores());
        var value = entry.getValue();
        return new AbstractMap.SimpleEntry<>(customer, value);
    }
}
