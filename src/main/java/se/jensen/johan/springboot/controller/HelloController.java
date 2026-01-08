package se.jensen.johan.springboot.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping
    public String hello() {
        return "Hello from spring boot";
    }

    @PostMapping
    public String post(@RequestBody String message) {
        return message + " received";
    }

}
