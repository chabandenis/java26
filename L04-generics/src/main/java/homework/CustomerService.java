package homework;

import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    TreeMap<Customer, String> map = new TreeMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> min = map.firstEntry();

        return new AbstractMap.SimpleEntry<>(
                new Customer(
                        min.getKey().getId(),
                        min.getKey().getName(),
                        min.getKey().getScores()),
                min.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {

        Map.Entry<Customer, String> max = map.higherEntry(customer);

        if (max == null) {
            return null;
        }

        return new AbstractMap.SimpleEntry<>(
                new Customer(
                        max.getKey().getId(),
                        max.getKey().getName(),
                        max.getKey().getScores()),
                max.getValue());
    }

    public void add(Customer customer, String data) {
        map.put(new Customer(customer.getId(), customer.getName(), customer.getScores()), data);
    }
}
