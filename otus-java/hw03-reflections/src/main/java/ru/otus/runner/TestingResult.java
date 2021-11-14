package ru.otus.runner;

final class TestingResult {
    private int passed = 0;
    private final int total;

    TestingResult(int total) {
        if (total <= 0) {
            throw new RuntimeException("\"total\" value should be greater than 0");
        }
        this.total = total;
    }

    public void addPassed() {
        if (passed == total) {
            throw new RuntimeException("Passed greater then total");
        }
        passed++;
    }

    public int getPassed() {
        return passed;
    }

    public int getFailed() {
        return (total - passed);
    }

    public int getTotal() {
        return total;
    }
}
