package ru.otus.hw.bankomat;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw.BankomatException;
import ru.otus.hw.Money;
import ru.otus.hw.kaseta.Kaseta;

public class Bankomat implements TakeMoneyBankomatInterface, StatusBankomatInterface, GiveMoneyBankomatInterface {

    private static final Logger log = LoggerFactory.getLogger(Bankomat.class);
    private Map<Money, Kaseta> kasetas; // список кассет

    public Bankomat() {
        // Пусть завод изготовитель поставляет банкомат сразу с касетами для денег.
        // Одна касета для одного номинала.
        kasetas = new EnumMap<>(Money.class);
        kasetas.put(Money.FIFTY, new Kaseta(Money.FIFTY));
        kasetas.put(Money.HUNDRED, new Kaseta(Money.HUNDRED));
        kasetas.put(Money.THOUSAND, new Kaseta(Money.THOUSAND));
        kasetas.put(Money.FIVE_THOUSAND, new Kaseta(Money.FIVE_THOUSAND));
    }

    @Override
    public void take(Map<Money, Integer> cash) {
        if (cash == null || cash.isEmpty()) {
            return;
        }

        for (var moneyValue : cash.entrySet()) {
            if (kasetas == null || kasetas.isEmpty()) {
                log.error("В банкомате нет кассет");
                continue;
            }
            kasetas.get(moneyValue.getKey()).addMoney(cash.get(moneyValue.getKey()));
        }
    }

    @Override
    public void print() {
        if (kasetas == null || kasetas.isEmpty()) {
            log.info("Касеты отсутствуют");
            return;
        }

        for (var kaseta : kasetas.entrySet()) {
            log.info(
                    "Номинал {}, количество купюр {} ",
                    kaseta.getKey(),
                    kaseta.getValue().getCount());
        }
    }

    @Override
    public Map<Money, Integer> give(Integer value) {
        Map<Money, Integer> givenMoney = new EnumMap<>(Money.class);

        // касеты в обратном порядке, чтобы выдавать сначала крупные купюры. Касеты с остатком
        List<Map.Entry<Money, Kaseta>> sortKasetas = kasetas.entrySet().stream()
                .filter(x -> x.getValue().getCount() > 0)
                .sorted((x, y) -> y.getKey().compareTo(x.getKey()))
                .toList();

        for (var kaseta : sortKasetas) {
            int count = Math.min(
                    kaseta.getValue().getCount(),
                    value / kaseta.getValue().getMoney().getValue());
            value = value - kaseta.getValue().getMoney().getValue() * count;
            kaseta.getValue().removeMoney(count);
            givenMoney.put(kaseta.getValue().getMoney(), count);
        }

        if (value > 0) {
            throw new BankomatException(
                    "В банкомате отсутствую деньги для выдачи суммы " + value + " рублей. Попробуйте другую сумму");
        }

        return givenMoney;
    }
}
