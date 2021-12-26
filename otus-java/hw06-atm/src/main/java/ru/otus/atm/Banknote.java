package ru.otus.atm;

import java.util.Objects;

public final class Banknote {
    private final Nominal nominal;

    public Banknote(Nominal nominal) {
        this.nominal = nominal;
    }

    public Nominal getNominal() {
        return nominal;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Banknote{");
        sb.append(nominal);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Banknote banknote = (Banknote) o;
        return nominal == banknote.nominal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nominal);
    }
}
