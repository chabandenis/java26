package ru.otus.hw;

import ru.otus.hw.annotation.After;
import ru.otus.hw.annotation.Before;
import ru.otus.hw.annotation.Test;

public class TestMe {

    @Before
    public void before() {
        System.out.println("*** Code of before");
    }

    @After
    public void after() {
        System.out.println("*** Code of after");
    }

    @Test
    public void functionOne() {
        System.out.println("*** Code of function one");
    }

    @Test
    public void functionTwo() {
        System.out.println("*** Code of function two");
    }

    @Test
    public void functionThree() {
        System.out.println("*** Code of function three");
    }

    @Override
    public String toString() {
        return "TestMe{}";
    }
}
