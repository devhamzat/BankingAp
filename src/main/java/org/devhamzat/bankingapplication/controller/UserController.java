package org.devhamzat.bankingapplication.controller;

import org.devhamzat.bankingapplication.dto.Response;
import org.devhamzat.bankingapplication.dto.UserRequest;
import org.devhamzat.bankingapplication.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/DevBank")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping(value = "/account/register")
    public Response createAccount(@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }
}

