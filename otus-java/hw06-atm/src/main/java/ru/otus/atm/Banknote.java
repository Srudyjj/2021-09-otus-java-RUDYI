package ru.otus.atm;

public class Banknote {
    private final Nominal nominal;

    public Banknote(Nominal nominal) {
        this.nominal = nominal;
    }

    public Nominal getNominal() {
        return nominal;
    }
}
