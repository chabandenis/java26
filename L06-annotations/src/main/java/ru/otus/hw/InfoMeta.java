package ru.otus.hw;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Служебная информация для тестов
 */
public class InfoMeta {
    private List<Method> beforeList;
    private List<Method> methodsForTest;
    private List<Method> afterList;
    private Object instance;
    private Class<?> clazz;

    public List<Method> getBeforeList() {
        return beforeList;
    }

    public List<Method> getMethodsForTest() {
        return methodsForTest;
    }

    public List<Method> getAfterList() {
        return afterList;
    }

    public Object getInstance() {
        return instance;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setBeforeList(List<Method> beforeList) {
        this.beforeList = beforeList;
    }

    public void setMethodsForTest(List<Method> methodsForTest) {
        this.methodsForTest = methodsForTest;
    }

    public void setAfterList(List<Method> afterList) {
        this.afterList = afterList;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
