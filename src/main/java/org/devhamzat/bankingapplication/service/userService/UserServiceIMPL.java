package org.devhamzat.bankingapplication.service.userService;

import org.devhamzat.bankingapplication.dto.*;
import org.devhamzat.bankingapplication.entity.User;
import org.devhamzat.bankingapplication.exceptions.AccountNotExisting;
import org.devhamzat.bankingapplication.exceptions.InsufficientFundsException;
import org.devhamzat.bankingapplication.exceptions.InvalidAccountCreation;
import org.devhamzat.bankingapplication.repository.UserRepository;
import org.devhamzat.bankingapplication.service.emailService.EmailService;
import org.devhamzat.bankingapplication.service.transaction.TransactionService;
import org.devhamzat.bankingapplication.utils.enums.TransactionTypes;
import org.devhamzat.bankingapplication.utils.numberGenerator.AccountNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class UserServiceIMPL implements UserService {
    private AccountNumberGenerator accountNumberGenerator;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    TransactionService transactionService;

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

    @Override
    public Response creditAccount(CreditANDDebitRequest creditANDDebitRequest) {
        boolean isAccountExisting = userRepository.existsByAccountNumber(creditANDDebitRequest.getAccountNumber());
        if (!isAccountExisting) {
            throw new AccountNotExisting("Account does not exist");
        }
        User userToCredit = userRepository.findByAccountNumber(creditANDDebitRequest.getAccountNumber());
        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(creditANDDebitRequest.getAmount()));
        userRepository.save(userToCredit);
        TransactionDto transactionDto = TransactionDto.builder()
                .accountNumber(userToCredit.getAccountNumber())
                .transactionType(TransactionTypes.credit)
                .amount(creditANDDebitRequest.getAmount())
                .build();
        transactionService.saveTransaction(transactionDto);
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(userToCredit.getEmail())
                .subject("Credit Alert")
                .body(creditANDDebitRequest.getAmount() + " successfully credited into your account. " + "\n Your balance is currently " + userToCredit.getAccountBalance())
                .build();
        emailService.sendEmailAlert(emailDetails);
        return Response.builder()
                .responseCode("200")
                .responseMessage(creditANDDebitRequest.getAmount() + " successfully credit ")
                .accountInfo(AccountInfo.builder()
                        .accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName() + " " + userToCredit.getOtherName())
                        .accountBalance(userToCredit.getAccountBalance())
                        .accountNumber(creditANDDebitRequest.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public Response DebitAccount(CreditANDDebitRequest creditANDDebitRequest) {
        boolean isAccountExisting = userRepository.existsByAccountNumber(creditANDDebitRequest.getAccountNumber());
        if (!isAccountExisting) {
            return Response.builder()
                    .responseCode("200")
                    .responseMessage("Account found")
                    .accountInfo(null)
                    .build();
        }
        User userToDebit = userRepository.findByAccountNumber(creditANDDebitRequest.getAccountNumber());
        BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger();
        BigInteger debitAmount = creditANDDebitRequest.getAmount().toBigInteger();
        if (availableBalance.intValue() < debitAmount.intValue()) {
            throw new InsufficientFundsException("Insufficient Funds ");
        } else {
            userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(creditANDDebitRequest.getAmount()));
            userRepository.save(userToDebit);
            TransactionDto transactionDto = TransactionDto.builder()
                    .accountNumber(userToDebit.getAccountNumber())
                    .transactionType(TransactionTypes.debit)
                    .amount(creditANDDebitRequest.getAmount())
                    .build();
            transactionService.saveTransaction(transactionDto);
            return Response.builder()
                    .responseCode("200")
                    .responseMessage("Debited successfully")
                    .accountInfo(AccountInfo.builder()
                            .accountNumber(creditANDDebitRequest.getAccountNumber())
                            .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName() + " " + userToDebit.getOtherName())
                            .accountBalance(userToDebit.getAccountBalance())
                            .build())
                    .build();
        }

    }

    @Override
    public Response transfer(TransferRequest transferRequest) {
        boolean isReceiversAccountExisting = userRepository.existsByAccountNumber(transferRequest.getReceiversAccountNumber());
        if (!isReceiversAccountExisting) {
            throw new AccountNotExisting("Receivers account not found");
        }
        User senderAccount = userRepository.findByAccountNumber(transferRequest.getSendersAccountNumber());
        if (transferRequest.getAmount().compareTo(senderAccount.getAccountBalance()) > 0) {
            return Response.builder()
                    .responseCode("200")
                    .responseMessage("Transfer successful")
                    .accountInfo(null)
                    .build();
        }
        senderAccount.setAccountBalance(senderAccount.getAccountBalance().subtract(transferRequest.getAmount()));
        String senderUserName = senderAccount.getFirstName() + " " + senderAccount.getLastName() + " " + senderAccount.getOtherName();
        userRepository.save(senderAccount);
        EmailDetails debitAlert = EmailDetails.builder()
                .subject("DEBIT ALERT")
                .recipient(senderAccount.getEmail())
                .body("The sum of " + transferRequest.getAmount() + " has been deducted from your account! Your current balance is " + senderAccount.getAccountBalance())
                .build();
        emailService.sendEmailAlert(debitAlert);
        User receiverAccount = userRepository.findByAccountNumber(transferRequest.getReceiversAccountNumber());
        receiverAccount.setAccountBalance(receiverAccount.getAccountBalance().add(transferRequest.getAmount()));
        userRepository.save(receiverAccount);
        EmailDetails creditAlert = EmailDetails.builder()
                .subject("CREDIT ALERT!")
                .recipient(senderAccount.getEmail())
                .body("The sum of " + transferRequest.getAmount() + " has been sent to you from " + senderUserName + "Your current balance is " + receiverAccount.getAccountBalance())
                .build();
        emailService.sendEmailAlert(creditAlert);
        TransactionDto transactionDto = TransactionDto.builder()
                .accountNumber(receiverAccount.getAccountNumber())
                .transactionType(TransactionTypes.credit)
                .amount(transferRequest.getAmount())
                .build();
        transactionService.saveTransaction(transactionDto);
        return Response.builder()
                .responseCode("200")
                .responseMessage("Transfer successful")
                .accountInfo(null)
                .build();

    }

}
