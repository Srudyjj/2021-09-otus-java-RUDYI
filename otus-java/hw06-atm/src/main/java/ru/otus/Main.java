package ru.otus;

import ru.otus.atm.*;

public class Main {
    public static void main(String[] args) {
        Storage storage = new StorageImpl();
        CashMachine cashMachine = new CashMachine(storage);

        cashMachine.add(new Banknote(Nominal.ONE), 10);
        cashMachine.add(new Banknote(Nominal.HUNGERED), 10);

        cashMachine.get(103);

        cashMachine.balance();

    }
}
