package com.airbnb;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Admin {

    public static void main(String[] args) {
        String password = BCrypt.hashpw("testing", BCrypt.gensalt(10));
        System.out.println(password);
    }
}
