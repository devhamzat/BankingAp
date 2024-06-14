package org.devhamzat.bankingapplication.exceptions;

public class DepositException extends RuntimeException{
    public DepositException(String error){
        super(error);
    }
}
