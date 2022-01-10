package ru.otus.processor;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


class ProcessorThrowExceptionTest {

    @Test
    void testException() {
        //TimeProvider provider = LocalTime::now;
        TimeProvider provider = Mockito.mock(TimeProvider.class);
        when(provider.getTime())
                .thenReturn(LocalTime.of(1, 2, 4));

        ProcessorThrowException processor = new ProcessorThrowException(provider);
        assertThrows(RuntimeException.class, () -> processor.process(null));
    }

    @Test
    void testIgnore() {
        //TimeProvider provider = LocalTime::now;
        TimeProvider provider = Mockito.mock(TimeProvider.class);
        when(provider.getTime())
                .thenReturn(LocalTime.of(1, 2, 3));

        ProcessorThrowException processor = new ProcessorThrowException(provider);
        processor.process(null);
    }

}