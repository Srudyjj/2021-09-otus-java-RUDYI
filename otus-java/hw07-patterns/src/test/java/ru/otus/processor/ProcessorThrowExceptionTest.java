package ru.otus.processor;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.mockito.Mockito.when;


class ProcessorThrowExceptionTest {

    @Test
    void testException() {
        TimeProvider provider = Mockito.mock(TimeProvider.class);
        when(provider.getTime())
                .thenReturn(LocalTime.of(0, 0, 0))
                .thenReturn(LocalTime.of(0, 0, 1))
                .thenReturn(LocalTime.of(0, 0, 2))
                .thenReturn(LocalTime.of(0, 0, 3))
                .thenReturn(LocalTime.of(0, 0, 4));

        ProcessorThrowException processor = new ProcessorThrowException(provider);

        assertTimeoutPreemptively(
                Duration.ofSeconds(4),
                () -> assertThrows(RuntimeException.class, () -> processor.process(null))
        );
    }

}