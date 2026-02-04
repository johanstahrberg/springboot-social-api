package se.jensen.johan.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for admin endpoints.
 * Only users with admin role can access these endpoints.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    /**
     * Endpoint for admin users.
     * Returns a simple message if you are allowed to access it.
     *
     * @return response with a message
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<String> adminOnly() {
        return ResponseEntity.ok().body("Admin page / admin endpoint OK");
    }
}