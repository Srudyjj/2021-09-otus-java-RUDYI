package ru.otus.atm;

import java.util.List;

public class CashMachine {
    private final Storage storage;

    public CashMachine(Storage storage) {
        this.storage = storage;
    }

    public void add(Banknote banknote, int value) {
        storage.add(banknote, value);
        System.out.println("The money added");
        System.out.println();
    }

    public void get(int value) {
        List<Banknote> banknotes = storage.withdraw(value);
        System.out.println("Get your money:");
        banknotes.forEach(System.out::println);
        System.out.println();
    }

    public void balance() {
        int sumOfRest = storage.getSumOfRest();
        System.out.println("Balance is: " + sumOfRest);
        System.out.println();
    }
}
