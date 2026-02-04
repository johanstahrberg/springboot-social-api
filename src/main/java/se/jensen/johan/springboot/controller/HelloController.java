package se.jensen.johan.springboot.controller;

import org.springframework.web.bind.annotation.*;

/**
 * Simple test controller.
 * Used to test that the application is running.
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    /**
     * Returns a test message.
     *
     * @return test message
     */
    @GetMapping
    public String hello() {
        return "Hello from spring boot";
    }

    /**
     * Returns the message.
     *
     * @param message request message
     * @return response message
     */
    @PostMapping
    public String post(@RequestBody String message) {
        return message + " received";
    }
}