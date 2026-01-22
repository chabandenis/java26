package ru.otus;

import com.google.common.base.Joiner;
import java.util.Arrays;

@SuppressWarnings("java:S106")
public class HelloOtus {
    public static void main(String... args) {
        String forPrint = Joiner.on(", ").join(Arrays.asList("Денис", "Роман", "Матвей"));
        System.out.println(forPrint);
    }
}
