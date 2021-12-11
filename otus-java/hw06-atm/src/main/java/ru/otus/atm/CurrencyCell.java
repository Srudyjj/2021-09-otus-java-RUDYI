package ru.otus.atm;

public class CurrencyCell {

    private final Nominal nominal;

    private int quantity;

    public CurrencyCell(Nominal nominal) {
        this.nominal = nominal;
    }

    public void add(int value) {
        quantity += value;
    }
}
