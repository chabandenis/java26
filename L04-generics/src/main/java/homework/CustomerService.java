package homework;

import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;

// @SuppressWarnings({"java:S1186", "java:S1135", "java:S1172"}) // при выполнении ДЗ эту аннотацию надо удалить
public class CustomerService {

    // todo: 3. надо реализовать методы этого класса
    // важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    TreeMap map = new TreeMap<Customer, String>();

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> min = map.firstEntry();

        Map.Entry<Customer, String> entry = new AbstractMap.SimpleEntry<>(
                new Customer(
                        min.getKey().getId(),
                        new String(min.getKey().getName()),
                        min.getKey().getScores()),
                min.getValue());

        return entry; // это "заглушка, чтобы скомилировать"
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {

        Map.Entry<Customer, String> max = map.higherEntry(customer);

        Map.Entry<Customer, String> entry = new AbstractMap.SimpleEntry<>(
                new Customer(
                        max.getKey().getId(),
                        new String(max.getKey().getName()),
                        max.getKey().getScores()),
                max.getValue());
        return entry;
    }

    public void add(Customer customer, String data) {
        map.put(new Customer(customer.getId(), new String(customer.getName()), customer.getScores()), data);
    }
}
