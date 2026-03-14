package ru.otus.hw.bankomat;

import java.util.Map;
import ru.otus.hw.Money;

/**
 *  Внести деньги на счет
 */
public interface TakeMoneyBankomatInterface {
    void take(Map<Money, Integer> cash);
}
