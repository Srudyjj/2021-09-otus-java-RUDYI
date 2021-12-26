package ru.otus.atm;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StorageImplTest {

    @Test
    void withdraw() {
        StorageImpl storage = new StorageImpl();
        storage.add(new Banknote(Nominal.HUNGERED), 0);
        storage.add(new Banknote(Nominal.FIFTY), 2);
        storage.add(new Banknote(Nominal.TWENTY), 2);
        storage.add(new Banknote(Nominal.TEN), 2);
        storage.add(new Banknote(Nominal.FIVE), 1);
        storage.add(new Banknote(Nominal.TWO), 1);
        storage.add(new Banknote(Nominal.ONE), 1);

        int value = 80;
        List<Banknote> res = storage.withdraw(value);
        int sum = res.stream().mapToInt(b -> b.getNominal().getValue()).sum();
        assertEquals(value, sum);
    }

    @Test
    void withdrawWithError() {
        StorageImpl storage = new StorageImpl();
        storage.add(new Banknote(Nominal.FIFTY), 2);
        storage.add(new Banknote(Nominal.TWENTY), 2);
        int value = 60;

        assertThrows(RuntimeException.class, () -> storage.withdraw(value));

        StorageImpl storageCopy = new StorageImpl();
        storageCopy.add(new Banknote(Nominal.FIFTY), 2);
        storageCopy.add(new Banknote(Nominal.TWENTY), 2);

        assertEquals(storage, storageCopy);
    }

    @Test
    void sum(){
        StorageImpl storage = new StorageImpl();
        storage.add(new Banknote(Nominal.FIFTY), 2);
        storage.add(new Banknote(Nominal.TWENTY), 2);

        var expected = 140;
        var actual = storage.getSumOfRest();
        assertEquals(expected, actual);
    }

    @Test
    void sumAfterWithdraw(){
        StorageImpl storage = new StorageImpl();
        storage.add(new Banknote(Nominal.FIFTY), 2);
        storage.add(new Banknote(Nominal.TWENTY), 2);

        var expected = 0;
        storage.withdraw(140);
        var actual = storage.getSumOfRest();
        assertEquals(expected, actual);
    }

    @Test
    void sumAfterErrorWithdraw(){
        StorageImpl storage = new StorageImpl();
        storage.add(new Banknote(Nominal.FIFTY), 2);
        storage.add(new Banknote(Nominal.TWENTY), 2);

        var expected = 140;
        assertThrows(RuntimeException.class, () -> {
            storage.withdraw(240);
        });
        var actual = storage.getSumOfRest();
        assertEquals(expected, actual);
    }
}