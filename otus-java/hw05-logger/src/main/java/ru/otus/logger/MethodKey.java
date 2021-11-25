package ru.otus.logger;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public final class MethodKey {
    private final String name;
    private final Class<?> returnType;
    private final Class<?>[] parameterTypes;

    private MethodKey(String name, Class<?> returnType, Class<?>[] parameterTypes) {
        this.name = name;
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
    }

    public static MethodKey of(Method method) {
        return new MethodKey(method.getName(), method.getReturnType(), method.getParameterTypes());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodKey methodKey = (MethodKey) o;
        return Objects.equals(name, methodKey.name)
                && Objects.equals(returnType, methodKey.returnType)
                && Arrays.equals(parameterTypes, methodKey.parameterTypes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, returnType);
        result = 31 * result + Arrays.hashCode(parameterTypes);
        return result;
    }
}
