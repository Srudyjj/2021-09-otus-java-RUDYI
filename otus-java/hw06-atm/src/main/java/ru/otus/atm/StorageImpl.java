package ru.otus.atm;

import ru.otus.atm.exception.CellNotFound;

import java.util.*;
import java.util.stream.Collectors;

public class StorageImpl implements Storage {
    private Map<Nominal, CurrencyCell> storage = new LinkedHashMap<>();

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
    public List<Banknote> withdraw(int value) {
        Map<Nominal, CurrencyCell> tempStorage = getStorageCopy(storage);
        List<Banknote> res = new ArrayList<>();
        Iterator<Map.Entry<Nominal, CurrencyCell>> iterator = tempStorage.entrySet().iterator();
        int counter = 0;
        while (iterator.hasNext() && counter < value) {
            Map.Entry<Nominal, CurrencyCell> next = iterator.next();
            int nominalVal = next.getKey().getValue();
            CurrencyCell cell = next.getValue();

            if (value >= nominalVal) {
                while (cell.size() > 0 && ((counter + nominalVal) <= value)) {
                    Banknote banknote = cell.remove();
                    res.add(banknote);
                    counter += nominalVal;
                }
            }
        }

        if (value != counter){
            throw new RuntimeException("The amount is not allowed");
        } else {
            storage = tempStorage;
        }

        return res;
    }

    private static Map<Nominal, CurrencyCell> getStorageCopy(Map<Nominal, CurrencyCell> storage) {
        return storage
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                                (entry) -> entry.getValue().copy(),
                                (x, y) -> y, LinkedHashMap::new));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StorageImpl{");
        sb.append("storage=").append(storage);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StorageImpl storage1 = (StorageImpl) o;
        return Objects.equals(storage, storage1.storage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storage);
    }
}
