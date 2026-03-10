package ru.otus.hw.kaseta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw.BankomatException;
import ru.otus.hw.Money;

public class Kaseta implements RemoveMoneyKasetaInteface, AddMoneyKasetaInteface {
    private static final Logger log = LoggerFactory.getLogger(Kaseta.class);

    private Money money;

    private Integer count;

    public Kaseta(Money money) {
        this.money = money;
        count = 0; // начальное количество купюр в кассете равно нулю
    }

    public Money getMoney() {
        return money;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public void removeMoney(int count) {
        this.count -= count;
        if (this.count < 0) {
            throw new BankomatException("Недостаточно средств");
        } else if (count > 0) {
            log.info("Успешно выдано {} купюр номиналом {}", count, money.getValue());
        }
    }

    @Override
    public void addMoney(int count) {
        this.count += count;
    }
}
