package com.ecommerce.backend.controller;

import java.util.List;
import java.util.Map;

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

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.entity.Customer;
import com.ecommerce.backend.services.CustomerService;



@RestController
@CrossOrigin("*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/addcustomer")
    public ResponseEntity<Object> addCustomer(@RequestBody Customer customer) {
        ResponseEntity<Object> response = customerService.saveCustomer(customer);
        return response;
    }

    @GetMapping("/allcustomer")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updatecustomer/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        if (updatedCustomer != null) {
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deletecustomer/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @PostMapping("/customerlogin")
//    public ResponseEntity<String> loginCustomer(@RequestBody Customer customerLoginRequest) {
//        return customerService.loginCustomer(customerLoginRequest);
//    }
    
//    @RequestMapping(value="/customerlogin", method=RequestMethod.POST)
//    @ResponseBody
//    public ResponseEntity<String> loginCustomer( @RequestParam Customer customerLoginRequest) {
//        return customerService.loginCustomer(customerLoginRequest);
//    }
    @PostMapping("/customerlogin")
    public ResponseEntity<String> loginCustomer(@RequestParam String email, @RequestParam String password) {
        return customerService.loginCustomer(email, password);
    }

    @PostMapping("/forgotpassword")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email is required");
        }
        return customerService.forgotPassword(email);
    }

    @PostMapping("/verifyotp")
    public ResponseEntity<String> verifyOTP(@RequestBody Map<String, String> requestBody) {
        String otp = requestBody.get("otp");
        return customerService.verifyOTP(otp);
    }
    
    @PostMapping("/changepassword")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> request) {
        String newPassword = request.get("newPassword");
        String email = request.get("email"); // Assuming email is passed from the client
        if (newPassword == null || email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("New password or email not provided");
        }
        return customerService.changePassword(newPassword, email);
    }
}
