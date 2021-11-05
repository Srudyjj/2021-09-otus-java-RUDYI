package ru.otus;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class HelloOtus {
    public static void main(String[] args) {
        Map<String, Integer> items = ImmutableMap.of("coin", 3, "glass", 4, "pencil", 1);
        items.entrySet().forEach(System.out::println);
    }
}
