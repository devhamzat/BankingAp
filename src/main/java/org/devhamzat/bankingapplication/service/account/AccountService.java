package org.devhamzat.bankingapplication.service.account;

import org.devhamzat.bankingapplication.entity.Account;
import org.devhamzat.bankingapplication.repository.AccountRepository;
import org.devhamzat.bankingapplication.service.serviceInterface.AccountServiceInterface;
import org.devhamzat.bankingapplication.utils.numberGenerator.AccountNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService implements AccountServiceInterface {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountNumberGenerator accountNumberGenerator;

    public AccountService(AccountRepository accountRepository, AccountNumberGenerator accountNumberGenerator) {
        this.accountRepository = accountRepository;
        this.accountNumberGenerator = accountNumberGenerator;
    }

    @Override
    public ResponseEntity<String> createAccount(Account account) {
        String accountNumber = accountNumberGenerator.generateAccountNumber();
        Optional<Account> accountOptional = accountRepository.findAccountByAccountNumber(account.getAccountNumber());
        if (accountOptional.isPresent()) {
            throw new IllegalStateException("account is present");
        }
        if (account.getAccountNumber() != accountNumber) {
            account.setAccountNumber(accountNumber);
        }

        accountRepository.save(account);
        return ResponseEntity.ok("Account successfully created with account number" + account.getAccountNumber());
    }
}
