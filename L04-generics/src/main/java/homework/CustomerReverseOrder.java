package homework;

import java.util.ArrayDeque;
import java.util.Deque;

// @SuppressWarnings({"java:S1186", "java:S1135", "java:S1172"}) // при выполнении ДЗ эту аннотацию надо удалить//
public class CustomerReverseOrder {

    Deque<Customer> deque = new ArrayDeque();

    public void add(Customer customer) {
        deque.addFirst(customer);
    }

    public Customer take() {
        return deque.poll();
    }
}
