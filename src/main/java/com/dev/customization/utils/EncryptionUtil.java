package com.dev.customization.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncryptionUtil {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encrypt(String data) {
        return passwordEncoder.encode(data);
    }

    public static boolean match(String rawData, String encodedData) {
        return passwordEncoder.matches(rawData, encodedData);
    }

}
