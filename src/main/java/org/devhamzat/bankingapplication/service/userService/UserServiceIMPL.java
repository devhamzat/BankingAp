package org.devhamzat.bankingapplication.service.userService;

import org.devhamzat.bankingapplication.dto.AccountInfo;
import org.devhamzat.bankingapplication.dto.Response;
import org.devhamzat.bankingapplication.dto.UserRequest;
import org.devhamzat.bankingapplication.entity.User;
import org.devhamzat.bankingapplication.exceptions.InvalidAccountCreation;
import org.devhamzat.bankingapplication.repository.UserRepository;
import org.devhamzat.bankingapplication.utils.numberGenerator.AccountNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceIMPL implements UserService {
    private AccountNumberGenerator accountNumberGenerator;
    @Autowired
    private UserRepository userRepository;

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
                .altPhoneNumber(userRequest.getPhoneNumber())
                .accountNumber(accountNumberGenerator.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .status("ACTIVE")
                .build();
        User savedUser = userRepository.save(newUser);
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
}
