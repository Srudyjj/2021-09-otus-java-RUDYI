package ru.otus.processor;

import ru.otus.model.Message;

import java.time.LocalTime;

public class ProcessorThrowException implements Processor {

    private final TimeProvider timeProvider;

    public ProcessorThrowException(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    @Override
    public Message process(Message message) {
        long startTime = System.nanoTime();
        LocalTime futureTime = timeProvider.getTime().plusSeconds(4);
        while (!futureTime.isBefore(timeProvider.getTime())){
            continue;
        }
        long endTime = System.nanoTime();
        System.out.println((endTime - startTime)/1_000_000);

        throw new RuntimeException();
    }
}
