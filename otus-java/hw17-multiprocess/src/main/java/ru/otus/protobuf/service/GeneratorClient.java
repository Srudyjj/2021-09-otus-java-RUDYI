package ru.otus.protobuf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.Response;

import java.util.Iterator;

public final class GeneratorClient {

    private static final Logger log = LoggerFactory.getLogger(GeneratorClient.class);

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
//                log.info("From server:{}", serverValue);
            }
        },"generator").start();
    }
}
