package org.example.minispringap12.controllers;

import org.example.minispringap12.dto.UserDTO;
import org.example.minispringap12.services.UserService;
import org.owasp.encoder.Encode;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated  // Enable validation on methods
public class HomeController {

    private final UserService userService;

    // Constructor-based injection
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home() {
        return "Welcome to the Mini Spring Boot Application with HTTPS!";
    }

    @PostMapping("/register")
    public String register(@RequestBody @Validated UserDTO userDTO) {
        // Sanitize inputs using OWASP encoder
        String sanitizedUsername = Encode.forHtml(userDTO.username());
        String sanitizedPassword = Encode.forHtml(userDTO.password());

        // Cache the registered user data
        return userService.updateUser(sanitizedUsername, sanitizedPassword);
    }

    @GetMapping("/getUser/{username}")
    public String getUser(@PathVariable String username) {
        return userService.getUser(Encode.forHtml(username));
    }

    @DeleteMapping("/evictUser/{username}")
    public String evictUserFromCache(@PathVariable String username) {
        userService.evictUserFromCache(Encode.forHtml(username));
        return "Evicted " + username + " from the cache";
    }
}
