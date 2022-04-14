package ru.otus.protobuf.service;

import ru.otus.protobuf.generated.Response;

import java.util.Iterator;

public final class GeneratorClient {

    private volatile int currentValue;
    private final Iterator<Response> iterator;

    public GeneratorClient(Iterator<Response> iterator) {
        this.iterator = iterator;
    }

    public synchronized int getCurrentValue() {
        return currentValue;
    }

    private synchronized void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public void start() {
        new Thread(() -> {
            while (iterator.hasNext()) {
                int serverValue = iterator.next().getServerValue();
                setCurrentValue(serverValue);
                System.out.println("From server -> " + serverValue);
            }
        },"generator").start();
    }
}
