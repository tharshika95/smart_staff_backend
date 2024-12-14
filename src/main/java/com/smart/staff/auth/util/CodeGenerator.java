package com.smart.staff.auth.util;

import java.security.SecureRandom;

public class CodeGenerator {

    public static String generate6DigitCode() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000); // Generates a number between 100000 and 999999
        return String.valueOf(code);
    }
}
