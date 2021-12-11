package ru.otus.atm;

import java.util.List;

public class CashMachine {
    private final Storage storage;

    public CashMachine(Storage storage) {
        this.storage = storage;
    }

    public void add(Banknote banknote, int value) {
        storage.add(banknote, value);
    }

    public List<Banknote> get(int value) {
        return storage.withdraw(value);
    }
}
