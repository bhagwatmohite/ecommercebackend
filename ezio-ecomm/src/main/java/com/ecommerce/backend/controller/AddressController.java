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

import com.ecommerce.backend.entity.Address;

import com.ecommerce.backend.services.AddressService;

@RestController
@CrossOrigin("*")
public class AddressController {

	 @Autowired
	    private AddressService addressService;

	    @GetMapping("/address")
	    public List<Address> getAllAddresses() {
	        return addressService.getAllAddresses();
	    }

	    @GetMapping("/address/{id}")
	    public ResponseEntity<?> getAddressById(@PathVariable Long id) {
	        Optional<Address> address = addressService.getAddressById(id);
	        if (address.isPresent()) {
	            return ResponseEntity.ok(address.get());
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address with ID " + id + " not found");
	        }
	    }
	    


//	    @PostMapping("/address")
//	    public Address saveAddress(@RequestBody Address address ) {
//	        return addressService.saveAddress(address);
//	    }
	    
	    @PostMapping("/save/{userId}")
	    public ResponseEntity<Address> saveAddress(@RequestBody Address address, @PathVariable("userId") Long userId) {
	        try {
	            Address savedAddress = addressService.saveAddress(address, userId);
	            return ResponseEntity.ok(savedAddress);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }

	    @DeleteMapping("/address/{id}")
	    public void deleteAddress(@PathVariable Long id) {
	        addressService.deleteAddress(id);
	    }

	    
	    //add adrresses by userid
	    @PutMapping("/address/{id}")
	    public ResponseEntity<?> updateAddress(@PathVariable Long id, @RequestBody Address address) {
	        try {
	            Address updatedAddress = addressService.updateAddress(id, address);
	            return ResponseEntity.ok(updatedAddress);
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        }
	    }
	    
	    
	    @GetMapping("/addresses/user/{userId}")
	    public ResponseEntity<?> getAddressesByUserId(@PathVariable Long userId) {
	        List<Address> addresses = addressService.getAddressesByUserId(userId);
	        if (!addresses.isEmpty()) {
	            return ResponseEntity.ok(addresses);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No addresses found for user with ID " + userId);
	        }
	    }
	    
	    //get latest address
	  
	    
}
