package ru.kata.spring.bootstrap.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class InitialPassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pass = "pass";
        String enPass = encoder.encode(pass);

        System.out.println(enPass);
    }
}
