package se.jensen.johan.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

/**
 * Configuration class for the application.
 * Provides beans that can be used in other parts of the program.
 */
@Configuration
public class MyConfig {


    /**
     * Creates a Scanner used to read input from the console.
     *
     * @return a Scanner instance
     */
    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }


}
