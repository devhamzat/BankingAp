package org.devhamzat.bankingapplication.service.serviceInterface;

import org.devhamzat.bankingapplication.entity.Account;
import org.springframework.http.ResponseEntity;

public interface AccountServiceInterface {
    ResponseEntity<String> createAccount(Account account);

}
