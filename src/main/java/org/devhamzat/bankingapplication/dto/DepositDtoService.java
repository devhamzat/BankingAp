package org.devhamzat.bankingapplication.dto;

import org.devhamzat.bankingapplication.entity.Account;
import org.devhamzat.bankingapplication.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepositDtoService {
    private static final Logger log = LoggerFactory.getLogger(DepositDtoService.class);
    @Autowired
    private AccountRepository accountRepository;

    public DepositRequest map(String accountNumber) {
        Optional<Account> optionalAccount = accountRepository.findAccountByAccountNumber(accountNumber);
        if (!optionalAccount.isPresent()) {
        }
        Account account = optionalAccount.get();
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAccountNumber(account.getAccountNumber());

        return depositRequest;
    }
}
