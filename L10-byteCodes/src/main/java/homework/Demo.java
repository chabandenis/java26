package homework;

public class Demo {
    public static void main(String[] args) {

        TestLoggingInterface testLogging = Ioc.createMyClass();

        testLogging.calculation(6);
        testLogging.calculation(7, 8);
        testLogging.calculation(9, 10, "11");

        testLogging.calculation(6);
        testLogging.calculation(7, 8);
        testLogging.calculation(9, 10, "11");
    }
}
