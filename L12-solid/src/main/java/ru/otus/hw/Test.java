package ru.otus.hw;

import java.util.EnumMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw.bankomat.Bankomat;

public class Test {
    private static final Logger log = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {

        Bankomat bankomat = new Bankomat();

        // Статус банкомата - пустто
        log.info("1. Статус банкомата");
        bankomat.print();

        // банкноты для пополнения
        Map<Money, Integer> cash = new EnumMap<>(Money.class);
        cash.put(Money.FIFTY, 10);
        cash.put(Money.HUNDRED, 10);
        cash.put(Money.THOUSAND, 10);
        cash.put(Money.FIVE_THOUSAND, 10);

        // принимать банкноты разных номиналов
        log.info("2. принять банкноты разных номиналов");
        bankomat.take(cash);

        // Статус банкомата - все 4 номинала по 10 штук
        log.info("3. Статус банкомата");
        bankomat.print();

        log.info("4. снять 50 рублей");
        bankomat.give(50);
        bankomat.print();

        log.info("5. снять 40 рублей");
        try {
            bankomat.give(40);
        } catch (BankomatException e) {
            log.error(e.getMessage());
        }
        bankomat.print();

        log.info("6. снять все");
        bankomat.give(5000 * 10 + 1000 * 10 + 100 * 10 + 50 * 9);
        bankomat.print();
    }
}
