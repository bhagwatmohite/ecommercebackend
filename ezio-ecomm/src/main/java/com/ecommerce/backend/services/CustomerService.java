package com.ecommerce.backend.services;

import com.ecommerce.backend.entity.Customer;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.repository.CustomerRepository;
import com.ecommerce.backend.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JavaMailSender emailSender;
    
    @Autowired
    private ProductRepository productRepository;
   

    private String otp = null;
    private String emailForVerification = null;

    public ResponseEntity<Object> saveCustomer(Customer customer) {
        Customer existingCustomer = customerRepository.findByEmail(customer.getEmail());
        if (existingCustomer != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Customer with email '" + customer.getEmail() + "' already exists");
        } else {
            Customer savedCustomer = customerRepository.save(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
        }
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

//    public Customer updateCustomer(Long id, Customer customer) {
//        return customerRepository.findById(id)
//                .map(existingCustomer -> {
//                    existingCustomer.setFirstName(customer.getFirstName());
//                    existingCustomer.setLastName(customer.getLastName());
//                    existingCustomer.setEmail(customer.getEmail());
//                    existingCustomer.setMobileNumber(customer.getMobileNumber());
//                    existingCustomer.setPassword(customer.getPassword());
//                    existingCustomer.setAddress(customer.getAddress());
//                    existingCustomer.setPincode(customer.getPincode());
//                    existingCustomer.setCity(customer.getCity());
//                    existingCustomer.setState(customer.getState());
//                    existingCustomer.setShopname(customer.getShopname());
//                    existingCustomer.setStatus(customer.getStatus());
//                    existingCustomer.setProducts(customer.getProducts());
//                    return customerRepository.save(existingCustomer);
//                })
//                .orElse(null);
//    }
    
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer != null) {
            existingCustomer.setFirstName(customer.getFirstName());
            existingCustomer.setLastName(customer.getLastName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setMobileNumber(customer.getMobileNumber());
          existingCustomer.setPassword(customer.getPassword());
          existingCustomer.setAddress(customer.getAddress());
          existingCustomer.setPincode(customer.getPincode());
          existingCustomer.setCity(customer.getCity());
          existingCustomer.setState(customer.getState());
          existingCustomer.setShopname(customer.getShopname());
          existingCustomer.setStatus(customer.getStatus());
      //    existingCustomer.setProducts(customer.getProducts());
            
            // Update associated products
            List<Product> products = existingCustomer.getProducts();
            if (products != null) {
                for (Product product : products) {
                    product.setCustomer(existingCustomer);
                    productRepository.save(product);
                }
            }
            
            return customerRepository.save(existingCustomer);
        }
        return null;
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

//    public ResponseEntity<String> loginCustomer(String email, String password) {
//        Customer existingCustomer = customerRepository.findByEmail(email);
//        if (existingCustomer != null && existingCustomer.getPassword().equals(password)) {
//            return ResponseEntity.ok("Successfully logged in");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body("Invalid email or password. Please try again.");
//        }
//    }
    public ResponseEntity<String> loginCustomer(String email, String password) {
        Customer existingCustomer = customerRepository.findByEmail(email);
        
        if (existingCustomer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password. Please try again.");
        }
        
        if (!existingCustomer.getStatus().equals("Active")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Your account is inactive. Please contact the owner for assistance.");
        }
        
        if (!existingCustomer.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password. Please try again.");
        }
        
        return ResponseEntity.ok("Successfully logged in");
    }

    public ResponseEntity<String> forgotPassword(String email) {
        // Validate if email exists in your database
        if (emailExistsInDatabase(email)) {
            // Generate OTP
            otp = generateOTP();
            emailForVerification = email;

            // Send OTP via email
            sendOTPEmail(email, otp);

            return ResponseEntity.ok("OTP sent to your email address");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No account found with the provided email address");
        }
    }

    public ResponseEntity<String> verifyOTP(String otp) {
        if (this.otp != null && emailForVerification != null) {
            if (this.otp.equals(otp)) {
                this.otp = null;
                emailForVerification = null;
                return ResponseEntity.ok("OTP verified successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid OTP provided");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("OTP or email not found for verification");
        }
    }

    private boolean emailExistsInDatabase(String email) {
        Customer existingCustomer = customerRepository.findByEmail(email);
        return existingCustomer != null;
    }

    private String generateOTP() {
        // Generate a 6-digit OTP
        return String.format("%06d", new Random().nextInt(999999));
    }

    private void sendOTPEmail(String email, String otp) {
        // Send OTP via email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP for password reset is: " + otp);
        emailSender.send(message);
    }

    public ResponseEntity<String> changePassword(String newPassword, String email) {
        try {
            if (email == null || !emailExistsInDatabase(email)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Email not found for password change verification");
            }            
            // Retrieve the existing customer
            Customer existingCustomer = customerRepository.findByEmail(email);            
            // Update the password
            existingCustomer.setPassword(newPassword);            
            // Save the updated customer
            customerRepository.save(existingCustomer);
            // Send email notification with the new password
            sendPasswordChangeEmail(email, newPassword);

            return ResponseEntity.ok("Password changed successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error changing password: " + e.getMessage());
        }
    }
    
    private void sendPasswordChangeEmail(String email, String newPassword) {
        // Send email notification with the new password
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Changed Successfully");
        message.setText("Your password has been changed successfully. Your new password is: " + newPassword);
        emailSender.send(message);
    }
}
