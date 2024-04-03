package org.devhamzat.bankingapplication.controller;

import org.devhamzat.bankingapplication.entity.Account;
import org.devhamzat.bankingapplication.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/DevBank")
public class Controller {
    @Autowired
    private AccountService accountService;

    public Controller(AccountService accountService) {
        this.accountService = accountService;
    }
    @PostMapping(value = "/account/register")
    public ResponseEntity<String> createAccount(@RequestBody Account account){
        ResponseEntity<String> responseEntity = accountService.createAccount(account);
        return responseEntity;
    }
}
