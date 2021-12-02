package ru.otus;

import ru.otus.logger.LogInvocationHandler;

import java.lang.reflect.Proxy;

public class LoggerDemo {
    public static void main(String[] args) {
        Calculator proxyInstance = (Calculator) Proxy.newProxyInstance(
                LoggerDemo.class.getClassLoader(), new Class[] { Calculator.class },
                new LogInvocationHandler(new TestClass()));

        proxyInstance.calculation(1);
        proxyInstance.calculation(2,3);
        proxyInstance.calculation(2,3, "Result");
    }
}
