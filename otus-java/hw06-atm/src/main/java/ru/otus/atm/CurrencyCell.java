package ru.otus.atm;

import java.util.LinkedList;
import java.util.Objects;

public class CurrencyCell {

    private final Nominal nominal;

    private final LinkedList<Banknote> list;

    private CurrencyCell(Nominal nominal, LinkedList<Banknote> list) {
        this.nominal = nominal;
        this.list = list;
    }

    public CurrencyCell(Nominal nominal) {
        this.nominal = nominal;
        this.list = new LinkedList<>();
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

    public CurrencyCell copy() {
        LinkedList<Banknote> newList = new LinkedList<>();
        newList.addAll(this.list);
        return new CurrencyCell(this.nominal, newList);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CurrencyCell{");
        sb.append("nominal=").append(nominal);
        sb.append(", size=").append(list.size());
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyCell that = (CurrencyCell) o;
        return nominal == that.nominal && Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nominal, list);
    }
}
