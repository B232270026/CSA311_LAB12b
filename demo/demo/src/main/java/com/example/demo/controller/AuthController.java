package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthController {
	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        try {
            User user = userService.registerUser(request.getUsername(), request.getPassword(), "USER");
            return ResponseEntity.ok("User registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage()); // Already taken
        }
    }

	

	@PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
    Authentication auth = null;
    try {
        auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    } catch (Exception e) {
        return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
    }

    if (auth.isAuthenticated()) {
        return ResponseEntity.ok("Login successful");
    } else {
        return ResponseEntity.status(401).body("Login failed");
    }
}

}

class RegisterRequest {
	private String username;
	private String password;

	// Getters and setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

class LoginRequest {
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}