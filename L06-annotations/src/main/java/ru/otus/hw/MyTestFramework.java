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

    private static final Logger log = LoggerFactory.getLogger(MyTestFramework.class);

    public void doTest(String className)
            throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        log.info("Тестируем класс {}", className);

        var clazz = Class.forName(className);

        // инициализация
        init(clazz);

        // выполнить тестирование
        doTests();

    }

    void init(Class<?> clazz) {
        // создать экземпляр после тестируемого класса
        instance = createObj(clazz);

        // список методов для тестирования
        methodsForTest = getMethodsForTest(clazz);

        // список методов до вызова теста
        beforeList = getMethodsBefore(clazz);

        // список методов после вызова теста
        afterList = getMethodsAfter(clazz);

    }

    // обработка тестовых методов
    void doTests() {
        methodsForTest.stream()
                .forEach(x -> doTest(x));
    }

    // обработка тестового метода
    void doTest(Method method) {

        // подготовительное окружение
        dosBefore();

        log.info("Выполняются подготовительные операции {}", beforeList);

        log.info("Выполняется метод {}", method);
        try {
            method.invoke(instance);
            log.info("Выполнен метод {}", method);
        } catch (IllegalAccessException e) {
            log.error("Ошибка выполнения метода {}, {}", method, e);
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            log.error("Ошибка выполнения метода {}, {}", method, e);
            throw new RuntimeException(e);
        }

        // завершающие операции
        dosaAfter();

    }

    void dosBefore() {
        beforeList.stream()
                .forEach(x -> doBefore(x));
    }

    void doBefore(Method method) {
        log.info("Выполняется подготовительный метод {}", method);
        try {
            method.invoke(instance);
            log.info("выполнен подготовительный метод {}", method);
        } catch (IllegalAccessException e) {
            log.error("Ошибка выполнения подготовительного метода {}, {}", method, e);
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            log.error("Ошибка выполнения подготовительного метода {}, {}", method, e);
            throw new RuntimeException(e);
        }
    }


    void dosaAfter() {
        beforeList.stream()
                .forEach(x -> doAfter(x));
    }

    void doAfter(Method method) {
        log.info("Выполняется заключительный метод {}", method);
        try {
            method.invoke(instance);
            log.info("выполнен заключительный метод {}", method);
        } catch (IllegalAccessException e) {
            log.error("Ошибка выполнения заключительного метода {}, {}", method, e);
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            log.error("Ошибка выполнения заключительного метода {}, {}", method, e);
            throw new RuntimeException(e);
        }
    }


/*

        for (Method method : methodsForTest) {
            try {
                for (Method before : beforeList) {
                    before.invoke(instance);
                }

                method.invoke(instance);

                for (Method after : afterList) {
                    after.invoke(instance);
                }
            } catch (Exception e) {
                log.error("Ошибка при выполнении теста", e);
            }
        }

 */

    List<Method> getMethodsForTest(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(x -> x.isAnnotationPresent(Test.class))
                .toList();
    }

    List<Method> getMethodsBefore(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(x -> x.isAnnotationPresent(Before.class))
                .toList();
    }

    List<Method> getMethodsAfter(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(x -> x.isAnnotationPresent(After.class))
                .toList();
    }

    Object createObj(Class<?> clazz) {
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

        return instance;
    }

    public static void main(String[] args)
            throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        MyTestFramework test = new MyTestFramework();
        test.doTest("ru.otus.hw.TestMe");
    }
}
