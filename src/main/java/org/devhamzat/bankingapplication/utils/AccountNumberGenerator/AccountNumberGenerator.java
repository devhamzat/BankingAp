package org.devhamzat.bankingapplication.utils.AccountNumberGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Random;

@Service
public class AccountNumberGenerator {
    private static final String PREFIX = "315";
    public static String generateAccountNumber() {
        Random random = new Random();


        int randomNumber = 1000000 + random.nextInt(9000000);


        String randomNumberString = String.valueOf(randomNumber);


        return PREFIX + randomNumberString;
    }
}
