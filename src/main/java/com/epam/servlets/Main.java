package com.epam.servlets;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        String password = "12345";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        if (passwordEncoder.matches(password, hashedPassword)) {
            System.out.println("cool");
        }
        System.out.println(hashedPassword);
        logger.error("cool");
    }
}
