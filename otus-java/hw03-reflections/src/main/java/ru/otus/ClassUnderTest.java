package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class ClassUnderTest {

    @Before
    public void before() {
        System.out.println("Before method");
    }

    @After
    public void after() {
        System.out.println("After method");
    }

    @Test
    public void first() {
        System.out.println("First method");
    }

    @Test
    public void second() {
        System.out.println("Second method");
    }

    @Test
    public void third() {
        System.out.println("Third method");
    }
}
