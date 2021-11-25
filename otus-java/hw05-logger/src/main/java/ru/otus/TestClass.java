package ru.otus;

import ru.otus.logger.Log;

public class TestClass implements Calculator {

    @Log
    @Override
    public void calculation(int param1) {
    }

    @Override
    public void calculation(int param1, int param2) {
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
    }
}
