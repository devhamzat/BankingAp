package org.devhamzat.bankingapplication.exceptions;

public class AccountNotExisting extends RuntimeException{
    public AccountNotExisting(String error){
        super(error);
    }
}
