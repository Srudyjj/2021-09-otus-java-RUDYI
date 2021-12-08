package ru.otus.atm;

import java.util.List;

public interface Storage {
    void add(Banknote banknote, int value);

    List<Banknote> withdraw(int value);
}
