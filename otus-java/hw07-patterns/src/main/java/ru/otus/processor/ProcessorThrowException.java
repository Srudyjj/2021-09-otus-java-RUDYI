package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorThrowException implements Processor {

    private final TimeProvider timeProvider;

    public ProcessorThrowException(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    @Override
    public Message process(Message message) {
        int startTime = timeProvider.getTime().getSecond();
        int tickTime = timeProvider.getTime().getSecond();
        while (tickTime - startTime < 4) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tickTime = timeProvider.getTime().getSecond();
        }

        throw new RuntimeException();
    }
}
