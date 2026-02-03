package ru.otus.hw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw.annotation.After;
import ru.otus.hw.annotation.Before;
import ru.otus.hw.annotation.Test;

public class TestMe {
    private static final Logger log = LoggerFactory.getLogger(TestMe.class);

    @Before
    public void before() {
        log.debug("\t\t *** Code of before");
    }

    @After
    public void after() {
        log.debug("\t\t *** Code of after");
    }

    @Test
    public void functionOne() {
        log.debug("\t\t *** Code of function one");
    }

    @Test
    public void functionTwo() {
        log.debug("\t\t *** Code of function two");
    }

    @Test
    public void functionThree() {
        log.debug("\t\t *** Code of function three");
        throw new AppException("Принудительная остановка");
    }
}
