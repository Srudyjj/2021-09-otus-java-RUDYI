package ru.otus.atm;

import java.util.LinkedList;

public class CurrencyCell {

    private final Nominal nominal;

    private final LinkedList<Banknote> list = new LinkedList<>();

    public CurrencyCell(Nominal nominal) {
        this.nominal = nominal;
    }

    public void add(Banknote banknote) {
        list.add(banknote);
    }

    public Banknote remove() {
        return list.removeLast();
    }

    public int size() {
        return list.size();
    }
}
