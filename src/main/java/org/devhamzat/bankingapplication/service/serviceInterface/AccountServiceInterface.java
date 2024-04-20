package org.devhamzat.bankingapplication.service.serviceInterface;

import org.devhamzat.bankingapplication.entity.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AccountServiceInterface {
    ResponseEntity<String> createAccount(Account account);

}
