package com.ecommerce.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.ecommerce.backend.entity.Userr;
import com.ecommerce.backend.repository.UserrRepository;

@Service
public class UserrService {

	
	  @Autowired
	    private UserrRepository userRepository;
	  
	
	  
	  
	  public ResponseEntity<Object> registerUser(Userr newUser) {
	        Optional<Userr> existingUserOptional = userRepository.findByEmail(newUser.getEmail());
	        if (existingUserOptional.isPresent()) {
	            // User with this email already exists, return a message
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                    .body("User with email '" + newUser.getEmail() + "' already exists");
	        } else {
	            // User does not exist, save the new user
	            Userr savedUser = userRepository.save(newUser);
	            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	        }
	    }
	  

	    public List<Userr> getAllUsers() {
	        return userRepository.findAll();
	    }

	    public Optional<Userr> getUserById(Long id) {
	        return userRepository.findById(id);
	    }

	    public Userr saveUser(Userr user) {
	        return userRepository.save(user);
	    }

	    public void deleteUser(Long id) {
	        userRepository.deleteById(id);
	    }

	    public Userr updateUser(Long id, Userr user) {
	        Optional<Userr> existingUserOptional = userRepository.findById(id);
	        if (existingUserOptional.isPresent()) {
	            Userr existingUser = existingUserOptional.get();
	            existingUser.setFname(user.getFname());
	            existingUser.setLname(user.getLname());
	            existingUser.setUsername(user.getUsername());
	            existingUser.setEmail(user.getEmail());
	            existingUser.setMobno(user.getMobno());
	            existingUser.setPassword(user.getPassword());
	            return userRepository.save(existingUser);
	        } else {
	            throw new RuntimeException("User with ID " + id + " not found");
	        }
	    }
	    
 
	    
	    
	    public boolean loginUser(String email, String password) {
	        Optional<Userr> userOptional = userRepository.findByEmail(email);
	        if (userOptional.isPresent()) {
	            Userr user = userOptional.get();
	            if (user.getPassword().equals(password)) {
	                return true;
	            } else {
	                throw new RuntimeException("Invalid password");
	            }
	        } else {
	            throw new RuntimeException("User not found");
	        }
	    }
	    
	   
	    	    

}
