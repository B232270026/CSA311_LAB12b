package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

    public User registerUser(String username, String password, String role) {
     
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already taken");
        }


		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole(role);

        return userRepository.save(user);
    }

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}