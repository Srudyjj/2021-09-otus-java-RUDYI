package ru.otus.runner;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class Runner<T> {

    private final List<Method> beforeList = new ArrayList<>();
    private final List<Method> afterList = new ArrayList<>();
    private final List<Method> testList = new ArrayList<>();

    private final Class<T> classUnderTest;

    private Runner(Class<T> classUnderTest) {
        this.classUnderTest = classUnderTest;
    }

    public static void run(Class<?> clazz) throws Exception {
        var runner = new Runner<>(clazz);
        runner.execute();
    }

    private void execute() throws Exception {
        int passed = 0;
        int failed = 0;
        prepareMethods();
        T object = createObject();
        for (Method test: testList){
            try {
                executeBefore(object);
                test.invoke(object);
                executeAfter(object);

                System.out.println(String.format("%s(): Passed", test.getName()));
                passed++;
            } catch (Exception e) {
                System.out.println(String.format("%s(): Failed", test.getName()));
                failed++;
            }
        }
        System.out.println(String.format("Total: %d test(s)", testList.size()));
        System.out.println(String.format("Passed: %d\nFailed: %d", passed, failed));
    }

    private void executeBefore(T object) throws Exception {
        for (Method method: beforeList){
            method.invoke(object);
        }
    }

    private void executeAfter(T object) throws Exception {
        for (Method method: afterList){
            method.invoke(object);
        }
    }

    private void prepareMethods() {
        Method[] methods = classUnderTest.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                beforeList.add(method);
            }
            if (method.isAnnotationPresent(After.class)) {
                afterList.add(method);
            }
            if (method.isAnnotationPresent(Test.class)) {
                testList.add(method);
            }
        }
    }

    private T createObject() throws Exception {
        return classUnderTest.getConstructor().newInstance();
    }
}