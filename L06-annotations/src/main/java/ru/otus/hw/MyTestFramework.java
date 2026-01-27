package ru.otus.hw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw.annotation.After;
import ru.otus.hw.annotation.Before;
import ru.otus.hw.annotation.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class MyTestFramework {

    private List<Method> beforeList;
    private List<Method> methodsForTest;
    private List<Method> afterList;
    private Object instance;
    private Class<?> clazz;
    private Result result = new Result();

    private static final Logger log = LoggerFactory.getLogger(MyTestFramework.class);

    public void doTest(String className) {
        log.info("Тестируем класс {}", className);

        // инициализация
        init(className);

        // выполнить тестирование
        doTests();

        // результаты
        log.info(result.toString());

    }

    private void init(String aClass) {
        // обнулить результаты тестов
        result.clearResult();

        try {
            clazz = Class.forName(aClass);
        } catch (ClassNotFoundException e) {
            log.error("Ошибка для класса {}, {}", aClass, e);
            throw new RuntimeException(e);
        }

        // список методов для тестирования
        methodsForTest = getMethodsForTest(clazz);

        // список методов до вызова теста
        beforeList = getMethodsBefore(clazz);

        // список методов после вызова теста
        afterList = getMethodsAfter(clazz);

    }

    // обработка тестовых методов
    private void doTests() {
        methodsForTest.stream()
                .forEach(x -> doTest(x));
    }

    // обработка тестового метода
    private void doTest(Method method) {
        log.info("======================");

        try {
            // создать экземпляр после тестируемого класса
            instance = createObj(clazz);

            // подготовительное окружение
            dosBefore();

            try {
                method.invoke(instance);
                log.info("Выполнен метод {}", method);
            } catch (IllegalAccessException e) {
                log.error("Ошибка выполнения метода {}", method, e);
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                log.error("Ошибка выполнения метода {}", method, e);
                throw new RuntimeException(e);
            }

            // успешный тест
            result.incPassed();

        } catch (RuntimeException e) {
            // ошибочный тест
            result.incFailed();
            log.info("!!! Ошибка при выполнении метода {}", method.getName(), e);
        }

        // завершающие операции
        if (instance != null) {
            dosaAfter();
        }
    }

    private void dosBefore() {
        beforeList.stream()
                .forEach(x -> doBefore(x));
    }

    private void doBefore(Method method) {
        try {
            method.invoke(instance);
            log.info("выполнен подготовительный метод {}", method);
        } catch (IllegalAccessException e) {
            log.error("Ошибка выполнения подготовительного метода {}", method, e);
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            log.error("Ошибка выполнения подготовительного метода {}", method, e);
            throw new RuntimeException(e);
        }
    }

    private void dosaAfter() {
        afterList.stream()
                .forEach(x -> doAfter(x));
    }

    private void doAfter(Method method) {
        try {
            method.invoke(instance);
            log.info("выполнен заключительный метод {}", method);
        } catch (IllegalAccessException e) {
            log.error("Ошибка выполнения заключительного метода {}", method, e);
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            log.error("Ошибка выполнения заключительного метода {}", method, e);
            throw new RuntimeException(e);
        }
    }

    private List<Method> getMethodsForTest(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(x -> x.isAnnotationPresent(Test.class))
                .toList();
    }

    private List<Method> getMethodsBefore(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(x -> x.isAnnotationPresent(Before.class))
                .toList();
    }

    private List<Method> getMethodsAfter(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(x -> x.isAnnotationPresent(After.class))
                .toList();
    }

    private Object createObj(Class<?> clazz) {
        Object instance = null;

        Constructor<?>[] constructors = clazz.getConstructors();

        for (Constructor<?> constructor : constructors) {
            try {
                instance = constructor.newInstance();
                log.info("Создали экземпляр {}", instance);
            } catch (Exception e) {
                log.error("Ошибка создания экземпляра", e);
            }
        }

        return instance;
    }

    public static void main(String[] args)
            throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        MyTestFramework test = new MyTestFramework();
        test.doTest("ru.otus.hw.TestMe");
    }
}

class Result {
    private int passed;
    private int failed;

    public void incPassed() {
        this.passed++;
    }

    public void incFailed() {
        this.failed++;
    }

    public void clearResult() {
        this.failed = 0;
        this.passed = 0;
    }

    @Override
    public String toString() {
        return "Result {" +
                "passed=" + passed +
                ", failed=" + failed +
                ", total=" + (failed + passed) +
                '}';
    }
}
