package org.devhamzat.bankingapplication.controller;

import org.devhamzat.bankingapplication.dto.*;
import org.devhamzat.bankingapplication.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/DevBank")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/account/register")
    public Response createAccount(@RequestBody UserRequest userRequest) {
        return userService.createAccount(userRequest);
    }

    @GetMapping(value = "/account/enquiry/balance")
    public Response balanceEnquiry(@RequestBody EnquiryRequest request) {
        return userService.balanceEnquiry(request);
    }


    @GetMapping(value = "/account/enquiry/name")
    public String nameEnquiry(@RequestBody EnquiryRequest request) {
        return userService.nameEnquiry(request);
    }


    @PostMapping("/account/credit")
    public Response creditAccount(@RequestBody CreditANDDebitRequest request) {
        return userService.creditAccount(request);
    }

    @PostMapping(value = "/account/debit")
    public Response debitAccount(@RequestBody CreditANDDebitRequest request) {
        return userService.DebitAccount(request);
    }

    @PostMapping(value = "/account/transfer")
    public Response transfer(@RequestBody TransferRequest request) {
        return userService.transfer(request);
    }
}

