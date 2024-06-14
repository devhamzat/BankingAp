package org.devhamzat.bankingapplication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.devhamzat.bankingapplication.entity.Account;

@Getter
@Setter
@NoArgsConstructor

public class DepositRequest {
    private Account account;
    private String accountNumber;
    private Double amount;
    public DepositRequest(String accountNumber, Double amount) {
        this.accountNumber = account.getAccountNumber();
        this.amount = amount;
    }
}
