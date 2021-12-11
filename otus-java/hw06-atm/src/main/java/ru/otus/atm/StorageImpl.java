package ru.otus.atm;

import ru.otus.atm.exception.CellNotFound;

import java.util.*;

public class StorageImpl implements Storage {
    private final Map<Nominal, CurrencyCell> storage = new LinkedHashMap<>();

    public StorageImpl() {
        storage.put(Nominal.HUNGERED, new CurrencyCell(Nominal.HUNGERED));
        storage.put(Nominal.FIFTY, new CurrencyCell(Nominal.FIFTY));
        storage.put(Nominal.TWENTY, new CurrencyCell(Nominal.TWENTY));
        storage.put(Nominal.TEN, new CurrencyCell(Nominal.TEN));
        storage.put(Nominal.FIVE, new CurrencyCell(Nominal.FIVE));
        storage.put(Nominal.TWO, new CurrencyCell(Nominal.TWO));
        storage.put(Nominal.ONE, new CurrencyCell(Nominal.ONE));
    }

    @Override
    public void add(Banknote banknote, int value) {
        Nominal nominal = banknote.getNominal();
        var currencyCell = Optional
                .ofNullable(storage.get(nominal))
                .orElseThrow(() -> new CellNotFound(nominal));
        for (int i = 1; i <= value; i++) {
            currencyCell.add(new Banknote(nominal));
        }
    }

    @Override
    public List<Banknote> withdraw(int value /*80*/) {
        List<Banknote> res = new ArrayList<>();
        Iterator<Map.Entry<Nominal, CurrencyCell>> iterator = storage.entrySet().iterator();
        int counter = 0;
        while (iterator.hasNext() && counter < value) {
            Map.Entry<Nominal, CurrencyCell> next = iterator.next();
            int nominalVal = next.getKey().getValue();
            CurrencyCell cell = next.getValue();

            if (value < nominalVal) {
                continue;
            } else if (value >= nominalVal) {
                while (cell.size() > 0 && ((counter + nominalVal) <= value)) {
                    Banknote banknote = cell.remove();
                    res.add(banknote);
                    counter += nominalVal;
                }
            }
        }
        return res;
    }
}
