package ru.otus.atm.exception;

import ru.otus.atm.Nominal;

public class CellNotFound extends RuntimeException {
    public CellNotFound(Nominal nominal) {
        super("Cell with type:" + nominal + "not found");
    }
}
