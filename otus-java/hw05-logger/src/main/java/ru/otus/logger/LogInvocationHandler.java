package ru.otus.logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LogInvocationHandler implements InvocationHandler {

    private final Map<MethodKey, Method> methods = new HashMap<>();

    private Object target;

    public LogInvocationHandler(Object target) {
        this.target = target;

        for (Method method : target.getClass().getMethods()) {
            if (method.isAnnotationPresent(Log.class)) {
                methods.put(MethodKey.of(method), method);
            }
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (methods.containsKey(MethodKey.of(method))) {
            String arguments = Arrays.asList(args)
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            System.out.println("executed method: " + method.getName() + ", params: " + arguments);
        }
        return method.invoke(target, args);
    }
}
