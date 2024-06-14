package org.devhamzat.bankingapplication.service.account;

import org.devhamzat.bankingapplication.entity.Account;
import org.devhamzat.bankingapplication.exceptions.DepositException;
import org.devhamzat.bankingapplication.exceptions.InvalidDepositAmount;
import org.devhamzat.bankingapplication.repository.AccountRepository;
import org.devhamzat.bankingapplication.service.serviceInterface.DepositServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepositService implements DepositServiceInterface {
    @Autowired
    private AccountRepository accountRepository;

    private Account account;


    public DepositService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;

    }

    @Override
    public Account deposit(String accountNumber, double amount) {

        Optional<Account> accountPresent = accountRepository.findAccountByAccountNumber(account.getAccountNumber());
        Account account = accountPresent.get();

        String databaseAccountNumber = account.getAccountNumber();
        if (!accountPresent.isPresent() || databaseAccountNumber != accountNumber) {
            throw new DepositException("account does not exist");
        }
        if (!ValidAmount(amount)) {
            throw new InvalidDepositAmount("Amount to be deposited is invalid");
        }
        account.setBalance(account.getBalance() + amount);

        return accountRepository.save(account);
    }

    @Override
    public boolean ValidAmount(Double amount) {
        return amount > 0;
    }
}

