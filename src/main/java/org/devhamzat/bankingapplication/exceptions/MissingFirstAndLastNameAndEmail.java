package org.devhamzat.bankingapplication.exceptions;

public class MissingFirstAndLastNameAndEmail extends RuntimeException {
    public MissingFirstAndLastNameAndEmail(String error) {
        super(error);
    }
}
