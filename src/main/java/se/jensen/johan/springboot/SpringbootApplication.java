package se.jensen.johan.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the Spring Boot application.
 * Starts the application.
 */
@SpringBootApplication
public class SpringbootApplication {

    /**
     * Application entry point.
     *
     * @param args application arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}