package org.devhamzat.bankingapplication.service.userService;

import org.devhamzat.bankingapplication.dto.*;
import org.devhamzat.bankingapplication.entity.User;
import org.devhamzat.bankingapplication.exceptions.AccountNotExisting;
import org.devhamzat.bankingapplication.exceptions.InvalidAccountCreation;
import org.devhamzat.bankingapplication.repository.UserRepository;
import org.devhamzat.bankingapplication.service.emailService.EmailService;
import org.devhamzat.bankingapplication.utils.numberGenerator.AccountNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceIMPL implements UserService {
    private AccountNumberGenerator accountNumberGenerator;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    EmailService emailService;

    public UserServiceIMPL(AccountNumberGenerator accountNumberGenerator) {
        this.accountNumberGenerator = accountNumberGenerator;
    }

    @Override
    public Response createAccount(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new InvalidAccountCreation("account is present");
        }
        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .email(userRequest.getEmail())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .altEmail(userRequest.getAltEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .altPhoneNumber(userRequest.getAltPhoneNumber())
                .accountNumber(accountNumberGenerator.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .status("ACTIVE")
                .dob(userRequest.getDob())
                .build();
        User savedUser = userRepository.save(newUser);
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .body("Congratulations! \n Account created successfully. \n Welcome to the DevBank family. \n Account Details: \n  " + "Account Name : " + savedUser.getFirstName() + " " + savedUser.getOtherName() + " " + savedUser.getLastName() + "\n Account Number : " + savedUser.getAccountNumber() + " \n Account status :  " + savedUser.getStatus())
                .build();
        emailService.sendEmailAlert(emailDetails);
        return Response.builder()
                .responseCode("200")
                .responseMessage("Account successfully created")
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName())
                        .build())
                .build();
    }

    @Override
    public Response balanceEnquiry(EnquiryRequest enquiryRequest) {
        boolean isAccountExisting = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if (!isAccountExisting) {
            throw new AccountNotExisting("Account not found");
        }
        User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return Response.builder()
                .responseCode("200")
                .responseMessage("Account Found")
                .accountInfo(AccountInfo.builder()
                        .accountName(foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName())
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(enquiryRequest.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        boolean isAccountExisting = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if (!isAccountExisting) {
            throw new AccountNotExisting("Account not found");
        }
        User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName();
    }

}
