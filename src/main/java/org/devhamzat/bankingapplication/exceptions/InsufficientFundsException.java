package org.devhamzat.bankingapplication.exceptions;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException(String error){
        super(error);
    }
}
