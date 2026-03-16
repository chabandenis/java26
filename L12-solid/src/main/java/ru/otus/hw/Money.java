package ru.otus.hw;

public enum Money {
    FIFTY(50),
    HUNDRED(100),
    THOUSAND(1000),
    FIVE_THOUSAND(5000);

    private final int value;

    Money(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
