package org.devhamzat.bankingapplication.utils.numberGenerator;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountNumberGenerator {
    private static final String PREFIX = "315";

    public String generateAccountNumber() {
        Random random = new Random();


        int randomNumber = 1000000 + random.nextInt(9000000);


        String randomNumberString = String.valueOf(randomNumber);


        return PREFIX + randomNumberString;
    }
}
