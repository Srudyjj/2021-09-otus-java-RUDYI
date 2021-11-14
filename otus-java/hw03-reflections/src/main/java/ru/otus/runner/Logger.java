package ru.otus.runner;

class Logger {

    public static void logPassed(String name) {
        System.out.printf("%s(): Passed%n", name);
    }

    public static void logFailed(String name) {
        System.out.printf("%s(): Failed%n", name);
    }

    public static void logResult(TestingResult result) {
        System.out.printf("Total: %d test(s)\nPassed: %d\nFailed: %d",
                result.getTotal(), result.getPassed(), result.getFailed());
    }
}
