package com.shubham.razorpay.common.util;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomizerUtil {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom(); //used secure random as it is thread safe and cryptographically strong random number generator
    public static String randomBase64(int length){

        byte[] buf = new byte[length];
        SECURE_RANDOM.nextBytes(buf);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);


    }
}
