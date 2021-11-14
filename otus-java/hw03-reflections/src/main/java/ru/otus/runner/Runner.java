package ru.otus.runner;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

public final class Runner {

    public static void run(Class<?> classUnderTest) throws Exception {
        TestingContext testingContext = prepareMethods(classUnderTest);
        Object object = createObject(classUnderTest);
        TestingResult result = new TestingResult(testingContext.getTotalTests());
        for (Method test : testingContext.getTestList()) {
            try {
                executeTest(object, test, testingContext);
                Logger.logPassed(test.getName());
                result.addPassed();
            } catch (Exception e) {
                Logger.logFailed(test.getName());
            }
        }
        Logger.logResult(result);
    }

    private static void executeTest(Object object, Method test, TestingContext testingContext) throws Exception {
        try {
            executeBefore(object, testingContext.getBeforeList());
            test.invoke(object);
        } finally {
            executeAfter(object, testingContext.getAfterList());
        }
    }

    private static void executeBefore(Object object, List<Method> beforeList) throws Exception {
        for (Method method : beforeList) {
            method.invoke(object);
        }
    }

    private static void executeAfter(Object object, List<Method> afterList) throws Exception {
        for (Method method : afterList) {
            method.invoke(object);
        }
    }

    private static TestingContext prepareMethods(Class<?> classUnderTest) {
        Method[] methods = classUnderTest.getDeclaredMethods();
        TestingContext context = new TestingContext();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                context.addBefore(method);
            }
            if (method.isAnnotationPresent(After.class)) {
                context.addAfter(method);
            }
            if (method.isAnnotationPresent(Test.class)) {
                context.addTest(method);
            }
        }
        return context;
    }

    private static Object createObject(Class<?> clazz) throws Exception {
        return clazz.getConstructor().newInstance();
    }
}