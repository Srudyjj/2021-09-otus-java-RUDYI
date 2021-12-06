package ru.otus.atm;

public class CashMachine {
    private final Storage storage;

    public CashMachine(Storage storage) {
        this.storage = storage;
    }

    public void add(Banknote banknote, int value) {
        storage.add(banknote, value);
    }
}
