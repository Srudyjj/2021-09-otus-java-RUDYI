package ru.otus.logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LogInvocationHandler implements InvocationHandler {

    private final Map<String, Method> methods = new HashMap<>();

    private Object target;

    public LogInvocationHandler(Object target) {
        this.target = target;

        for (Method method : target.getClass().getMethods()) {
            if (method.isAnnotationPresent(Log.class)) {
                methods.put(method.getName(), method);
            }
        }

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (methods.containsKey(method.getName())) {
            String arguments = Arrays.asList(args)
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            System.out.println("executed method: " + method.getName() + ", params: " + arguments);
        }
        System.out.println(method.getName());
        System.out.println(method.getReturnType());
        System.out.println(Arrays.toString(method.getParameterTypes()));

        return method.invoke(target, args);
    }

//      if (obj != null && obj instanceof Method) {
//        Method other = (Method)obj;
//        if ((getDeclaringClass() == other.getDeclaringClass())
//                && (getName() == other.getName())) {
//            if (!returnType.equals(other.getReturnType()))
//                return false;
//            return equalParamTypes(parameterTypes, other.parameterTypes);
//        }
//    }
//        return false;
//}

//    return equalParamTypes(parameterTypes, other.parameterTypes);

//    boolean equalParamTypes(Class<?>[] params1, Class<?>[] params2) {
//        /* Avoid unnecessary cloning */
//        if (params1.length == params2.length) {
//            for (int i = 0; i < params1.length; i++) {
//                if (params1[i] != params2[i])
//                    return false;
//            }
//            return true;
//        }
//        return false;
//    }
}
