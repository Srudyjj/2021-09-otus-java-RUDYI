package ru.otus.atm;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StorageImplTest {

    @Test
    void withdraw() {
        StorageImpl storage = new StorageImpl();
        storage.add(new Banknote(Nominal.HUNGERED), 0);
        storage.add(new Banknote(Nominal.FIFTY), 20);
        storage.add(new Banknote(Nominal.TWENTY), 20);
        storage.add(new Banknote(Nominal.TEN), 20);
        storage.add(new Banknote(Nominal.FIVE), 10);
        storage.add(new Banknote(Nominal.TWO), 10);
        storage.add(new Banknote(Nominal.ONE), 10);

        int value = 100;
        List<Banknote> res = storage.withdraw(value);
        int sum = res.stream().mapToInt(b -> b.getNominal().getValue()).sum();
        assertEquals(value, sum);

    }
}