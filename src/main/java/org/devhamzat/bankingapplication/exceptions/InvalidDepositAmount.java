package org.devhamzat.bankingapplication.exceptions;

public class InvalidDepositAmount extends RuntimeException{
    public InvalidDepositAmount(String error){
        super(error);
    }
}
