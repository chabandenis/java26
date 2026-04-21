package ru.otus.hw.bankomat;

import java.util.Map;
import ru.otus.hw.Money;

/**
 *  Снять деньги со счета
 */
public interface GiveMoneyBankomatInterface {
    Map<Money, Integer> give(Integer value);
}
