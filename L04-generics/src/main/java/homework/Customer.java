package homework;

// @SuppressWarnings({"java:S1135"}) // при выполнении ДЗ эту аннотацию надо удалить
public class Customer implements Comparable<Customer> {
    private final long id;
    private String name;
    private long scores;

    public Customer(long id, String name, long scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScores() {
        return scores;
    }

    public void setScores(long scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name='" + name + '\'' + ", scores=" + scores + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (id == customer.id) return true;
        return false;
    }

    @Override
    public int compareTo(Customer other) {
        return Long.compare(this.scores, other.scores); // сравнение по полю idother.id; // сравнение по полю id
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        return result;
    }
}
