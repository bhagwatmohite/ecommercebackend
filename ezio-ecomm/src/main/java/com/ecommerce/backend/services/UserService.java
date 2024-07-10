package com.ecommerce.backend.services;


import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
	
	 @Autowired
	    private UserRepository userRepository;

	    public ResponseEntity<Object> registerUser(User newUser) {
	        // Check if a user with the same email already exists
	        User existingUser = userRepository.findByEmail(newUser.getEmail());
	        if (existingUser != null) {
	            // User with this email already exists, return a message
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                    .body("User with email '" + newUser.getEmail() + "' already exists");
	        } else {
	            // User does not exist, save the new user
	            User savedUser = userRepository.save(newUser);
	            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	        }
	    }

	    public List<User> getAllUsers() {
	        return userRepository.findAll();
	    }

	    public User getUserById(Long id) {
	        return userRepository.findById(id).orElse(null);
	    }

	    public User updateUser(User updatedUser, Long id) {
	        return userRepository.findById(id)
	                .map(user -> {
	                    user.setUsername(updatedUser.getUsername());
	                    user.setEmail(updatedUser.getEmail());
	                    user.setPassword(updatedUser.getPassword());
	                    return userRepository.save(user);
	                })
	                .orElse(null);
	    }
	    

	    public String deleteUser(Long id) {
	        if (!userRepository.existsById(id)) {
	            return "User with id " + id + " does not exist";
	        }
	        userRepository.deleteById(id);
	        return "User with id " + id + " has been deleted successfully";
	    }

	    public ResponseEntity<String> loginUser(User userLoginRequest) {
	        // Retrieve user by email
	        User existingUser = userRepository.findByEmail(userLoginRequest.getEmail());

	        if (existingUser != null) {
	            // Check if the provided password matches
	            if (existingUser.getPassword().equals(userLoginRequest.getPassword())) {
	                // Passwords match, login successful
	                return ResponseEntity.ok("Successfully logged in");
	            } else {
	                // Password does not match
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                        .body("Invalid email or password. Please try again.");
	            }
	        } else {
	            // User with this email does not exist
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                    .body("Invalid email or password. Please try again.");
	        }
	    }

}
