package org.epde;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordDecoder {

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String originalPassword = "mySecretPassword";

        String encryptedPassword = encryptPassword(originalPassword);
        System.out.println("Encrypted Password: " + encryptedPassword);

        String inputPassword = "mySecretPassword";
        boolean isPasswordMatch = checkPassword(inputPassword, encryptedPassword);
        System.out.println("Password Match: " + isPasswordMatch);
    }

    private static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    private static boolean checkPassword(String inputPassword, String encryptedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(inputPassword, encryptedPassword);
    }
}

