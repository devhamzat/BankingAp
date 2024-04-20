package org.devhamzat.bankingapplication.service.account;

import org.devhamzat.bankingapplication.entity.Account;
import org.devhamzat.bankingapplication.exceptions.InvalidAccountCreation;
import org.devhamzat.bankingapplication.exceptions.MissingFirstAndLastNameAndEmail;
import org.devhamzat.bankingapplication.repository.AccountRepository;
import org.devhamzat.bankingapplication.service.serviceInterface.AccountServiceInterface;
import org.devhamzat.bankingapplication.utils.numberGenerator.AccountNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
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
    @Transactional
    public ResponseEntity<String> createAccount(Account account) {
        if (account.getAccountFirstName().isEmpty() || account.getAccountLastName().isEmpty() || account.getEmail().isEmpty()) {
            throw new MissingFirstAndLastNameAndEmail("first name, last name  and email can not be empty");
        }
        String accountNumber = accountNumberGenerator.generateAccountNumber();
        Optional<Account> accountOptional = accountRepository.findAccountByAccountNumber(account.getAccountNumber());

        if (accountOptional.isPresent()) {
            throw new InvalidAccountCreation("account is present");
        }
        if (!Objects.equals(account.getAccountNumber(), accountNumber)) {
            account.setAccountNumber(accountNumber);
        }
        accountRepository.save(account);
        return ResponseEntity.ok("Account successfully created with account number " + account.getAccountNumber());
    }
}
