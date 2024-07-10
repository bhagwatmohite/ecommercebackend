package com.ecommerce.backend.controller;

import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class UserController {

	 @Autowired
	    private UserService userService;

	 
	    //Api for register
	    @PostMapping("/registeruser")
	    public ResponseEntity<Object> registerUser(@RequestBody User newUser) {
	        return userService.registerUser(newUser);
	    }

	    @GetMapping("/allusers")
	    public List<User> getAllUsers() {
	        return userService.getAllUsers();
	    }

	    @GetMapping("/user/{id}")
	    public User getUserById(@PathVariable Long id) {
	        return userService.getUserById(id);
	    }

	    @PutMapping("/user/{id}")
	    public User updateUser(@RequestBody User updatedUser, @PathVariable Long id) {
	        return userService.updateUser(updatedUser, id);
	    }

	    @DeleteMapping("/deleteuser/{id}")
	    public String deleteUser(@PathVariable Long id) {
	        return userService.deleteUser(id);
	    }

	    //Api for the login
	    @PostMapping("/login")
	    public ResponseEntity<String> loginUser(@RequestBody User userLoginRequest) {
	        return userService.loginUser(userLoginRequest);
	    }
}
