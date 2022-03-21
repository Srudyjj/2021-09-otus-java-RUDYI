package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        try {
            processConfig(initialConfigClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void processConfig(Class<?> configClass) throws Exception {
        checkConfigClass(configClass);
        Object configInst = configClass.getConstructor().newInstance();

        for (Method method : getMethods(configClass)){
            Object bean;
            if (method.getParameterCount() > 0) {
                Object[] params = Stream.of(method.getParameterTypes())
                        .map(p -> Optional.ofNullable(getAppComponent(p))
                                .orElseThrow(() -> new RuntimeException("Can't find bean " + p.getName())))
                        .toArray();
                bean = method.invoke(configInst, params);
            } else {
                bean = method.invoke(configInst);
            }
            appComponents.add(bean);
            appComponentsByName.put(method.getName(), bean);
        }
    }

    private List<Method> getMethods(Class<?> configClass) {
        return Stream.of(configClass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(m -> m.getAnnotation(AppComponent.class).order()))
                .toList();
    }


    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        for (Object bean : appComponents) {
            if (bean.getClass().getName().equalsIgnoreCase(componentClass.getName())
            || compareToInterface(bean.getClass(), componentClass)) {
                return (C) bean;
            }
        }
        return null;
    }

    private static boolean compareToInterface(Class<?> beanClass, Class<?> foundClass) {
        for (Class<?> anInterface : beanClass.getInterfaces()) {
            if (anInterface.getName().equalsIgnoreCase(foundClass.getName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        if (appComponentsByName.containsKey(componentName)) {
            return (C) appComponentsByName.get(componentName);
        }
        return null;
    }
}
