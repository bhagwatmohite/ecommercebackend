package com.ecommerce.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.entity.Userr;
import com.ecommerce.backend.services.UserrService;

@RestController
@CrossOrigin("*")
public class UserrController {
	
	@Autowired
    private UserrService userService;
	

    @PostMapping("/registeruserr")
    public ResponseEntity<?> registerUser(@RequestBody Userr user) {
        return userService.registerUser(user);
    }

    @GetMapping("/userr")
    public List<Userr> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/userr/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<Userr> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + id + " not found");
        }
    }

    @PostMapping("/userr")
    public Userr saveUser(@RequestBody Userr user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/userr/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/userr/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Userr user) {
        try {
            Userr updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping("/userr/login")
    public ResponseEntity<?> loginUser(@RequestBody Userr loginRequest) {
        try {
            boolean loginSuccess = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
            if (loginSuccess) {
                return ResponseEntity.ok("Logged in successfully");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    
}
