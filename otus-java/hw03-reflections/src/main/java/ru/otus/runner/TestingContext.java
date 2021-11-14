package ru.otus.runner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

final class TestingContext {

    private final List<Method> beforeList = new ArrayList<>();
    private final List<Method> afterList = new ArrayList<>();
    private final List<Method> testList = new ArrayList<>();

    public void addBefore(Method m) {
        beforeList.add(m);
    }

    public List<Method> getBeforeList() {
        return List.copyOf(beforeList);
    }

    public void addAfter(Method m) {
        afterList.add(m);
    }

    public List<Method> getAfterList() {
        return List.copyOf(afterList);
    }

    public void addTest(Method m) {
        testList.add(m);
    }

    public List<Method> getTestList() {
        return List.copyOf(testList);
    }

    public int getTotalTests() {
        return testList.size();
    }
}
