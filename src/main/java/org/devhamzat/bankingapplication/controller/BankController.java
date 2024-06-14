//package org.devhamzat.bankingapplication.controller;
//
//import org.devhamzat.bankingapplication.dto.DepositRequest;
//import org.devhamzat.bankingapplication.entity.Account;
//import org.devhamzat.bankingapplication.service.account.CreateAccountService;
//import org.devhamzat.bankingapplication.service.account.DepositService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("api/DevBank")
//public class BankController {
//    @Autowired
//    private CreateAccountService createAccountService;
//    @Autowired
//    private DepositService depositService;
//
//    public BankController(CreateAccountService accountService, DepositService depositService) {
//        this.createAccountService = accountService;
//        this.depositService = depositService;
//    }
//
//    @PostMapping(value = "/account/register")
//    public ResponseEntity<String> createAccount(@RequestBody Account account) {
//
//
//        return createAccountService.createAccount(account);
//    }
//
//    @PostMapping(value = "/account/deposit")
//    public Account deposit(@RequestBody DepositRequest depositRequest) {
//
//        return depositService.deposit(depositRequest.getAccountNumber(), depositRequest.getAmount());
//    }
//
//}
