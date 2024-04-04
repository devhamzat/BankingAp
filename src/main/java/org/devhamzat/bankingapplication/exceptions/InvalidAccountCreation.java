package org.devhamzat.bankingapplication.exceptions;

public class InvalidAccountCreation extends RuntimeException{
    public InvalidAccountCreation(String error){
        super(error);
    }
}
