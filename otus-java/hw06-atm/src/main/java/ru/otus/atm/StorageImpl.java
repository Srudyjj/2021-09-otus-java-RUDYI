package ru.otus.atm;

import ru.otus.atm.exception.CellNotFound;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StorageImpl implements Storage {
    private final Map<Nominal, CurrencyCell> storage = new HashMap<>();

    public StorageImpl() {
        storage.put(Nominal.ONE, new CurrencyCell(Nominal.ONE));
        storage.put(Nominal.TWO, new CurrencyCell(Nominal.TWO));
        storage.put(Nominal.FIVE, new CurrencyCell(Nominal.FIVE));
        storage.put(Nominal.TEN, new CurrencyCell(Nominal.TEN));
        storage.put(Nominal.TWENTY, new CurrencyCell(Nominal.TWENTY));
        storage.put(Nominal.FIFTY, new CurrencyCell(Nominal.FIFTY));
        storage.put(Nominal.HUNGERED, new CurrencyCell(Nominal.HUNGERED));
    }

    @Override
    public void add(Banknote banknote, int value) {
        Nominal nominal = banknote.getNominal();
        var currencyCell = Optional
                .ofNullable(storage.get(nominal))
                .orElseThrow(() -> new CellNotFound(nominal));
        currencyCell.add(value);
    }
}
