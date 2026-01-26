package ru.otus.hw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyTestFramework {

    private static final Logger log = LoggerFactory.getLogger(MyTestFramework.class);

    public void doTest(String className) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        log.info("Тестируем класс {}", className);

        var clazz = Class.forName(className);
        Object instance = null;

        Constructor<?>[] constructors = clazz.getConstructors();

        for (Constructor<?> constructor : constructors) {
            try {
                instance = constructor.newInstance();
                log.info("Создали инстанс {}", instance);
            } catch (Exception e) {
                log.error("Ошибка создания инстанса", e);
            }
        }

        Method[] declaredMethods = clazz.getDeclaredMethods();

        for (Method method : declaredMethods) {
            log.info("Исполняю метод {}", method.getName() );
            method.invoke(instance);
        }



/*
        before();
        test1();
        after();

        before();
        test2();
        after();
*/
    }

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        MyTestFramework test = new MyTestFramework();
        test.doTest("ru.otus.hw.TestMe");
    }


}
