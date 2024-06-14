package org.devhamzat.bankingapplication.service.serviceInterface;

import org.devhamzat.bankingapplication.entity.Account;
import org.springframework.http.ResponseEntity;

public interface DepositServiceInterface {
    Account deposit(String accountNumber, double amount);

    boolean ValidAmount(Double amount);
}
