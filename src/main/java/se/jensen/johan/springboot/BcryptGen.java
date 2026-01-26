package se.jensen.johan.springboot;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptGen {
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("Test123!"));
    }
}