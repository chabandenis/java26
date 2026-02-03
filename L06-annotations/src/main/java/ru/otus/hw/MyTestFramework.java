package ru.otus.hw;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw.annotation.After;
import ru.otus.hw.annotation.Before;
import ru.otus.hw.annotation.Test;

public class MyTestFramework {

    private static final Logger log = LoggerFactory.getLogger(MyTestFramework.class);

    public void doTest(String className) {
        log.info("Тестируем класс {}", className);

        // выполнить тестирование
        InfoResults infoResults = doTests(
                // инициализация
                init(className));

        // результаты
        log.info(infoResults.getResultInfo());
    }

    private InfoMeta init(String aClass) {
        InfoMeta infoMeta = new InfoMeta();

        try {
            infoMeta.setClazz(Class.forName(aClass));
        } catch (ClassNotFoundException e) {
            throw new AppException(e.getMessage());
        }

        // список методов для тестирования
        infoMeta.setMethodsForTest(getMethodsForTest(infoMeta.getClazz()));

        // список методов до вызова теста
        infoMeta.setBeforeList(getMethodsBefore(infoMeta.getClazz()));

        // список методов после вызова теста
        infoMeta.setAfterList(getMethodsAfter(infoMeta.getClazz()));

        return infoMeta;
    }

    // обработка тестовых методов
    private InfoResults doTests(InfoMeta infoMeta) {
        InfoResults infoResults = new InfoResults();

        infoMeta.getMethodsForTest().stream().forEach(x -> doTest(x, infoMeta, infoResults));

        return infoResults;
    }

    // обработка тестового метода
    private void doTest(Method method, InfoMeta infoMeta, InfoResults infoResults) {
        log.info("======================");

        try {
            // создать экземпляр после тестируемого класса
            infoMeta.setInstance(createObj(infoMeta.getClazz()));

            // подготовительное окружение
            dosBefore(infoMeta);

            invokeMethod(method, infoMeta);

            // успешный тест
            infoResults.incPassed();

        } catch (RuntimeException e) {
            // ошибочный тест
            infoResults.incFailed();
            log.info("!!! Ошибка при выполнении метода {}", method.getName(), e);
        }

        // завершающие операции
        if (infoMeta.getInstance() != null) {
            dosaAfter(infoMeta);
        }
    }

    private static void invokeMethod(Method method, InfoMeta infoMeta) {
        try {
            method.invoke(infoMeta.getInstance());
            log.info("Выполнен метод {}", method);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AppException(e.getMessage());
        }
    }

    private void dosBefore(InfoMeta infoMeta) {
        infoMeta.getBeforeList().stream().forEach(x -> doBefore(x, infoMeta.getInstance()));
    }

    private void doBefore(Method method, Object instance) {
        try {
            method.invoke(instance);
            log.info("выполнен подготовительный метод {}", method);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AppException(e.getMessage());
        }
    }

    private void dosaAfter(InfoMeta infoMeta) {
        infoMeta.getAfterList().stream().forEach(x -> doAfter(x, infoMeta.getInstance()));
    }

    private void doAfter(Method method, Object instance) {
        try {
            method.invoke(instance);
            log.info("выполнен заключительный метод {}", method);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AppException(e.getMessage());
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

    public static void main(String[] args) {
        MyTestFramework test = new MyTestFramework();
        test.doTest("ru.otus.hw.TestMe");
    }
}
