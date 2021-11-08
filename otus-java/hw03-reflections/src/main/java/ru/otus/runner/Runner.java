package ru.otus.runner;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Runner {

    public static void run(Class<?> clazz) throws Exception {

        Object obj = clazz.getConstructor().newInstance();
        List<Method> beforeList = new ArrayList<>();
        List<Method> afterList = new ArrayList<>();
        List<Method> testList = new ArrayList<>();

        Method[] methods = clazz.getDeclaredMethods();
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



        testList.forEach(method -> {
            beforeList.forEach(before -> invokeMethod(before, obj));
            invokeMethod(method, obj);
            afterList.forEach(after -> invokeMethod(after, obj));
        });



    }

    private static void invokeMethod(Method method, Object obj) {
        try {
            method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
