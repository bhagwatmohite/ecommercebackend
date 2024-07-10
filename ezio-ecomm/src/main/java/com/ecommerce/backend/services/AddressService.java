package com.ecommerce.backend.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.entity.Userr;
import com.ecommerce.backend.repository.AddressRepository;
import com.ecommerce.backend.repository.UserrRepository;

@Service
public class AddressService {
	
	 @Autowired
	    private AddressRepository addressRepository;
	 
	 
	 @Autowired
	 private UserrRepository userrRepository;

	    public List<Address> getAllAddresses() {
	        return addressRepository.findAll();
	    }

	    public Optional<Address> getAddressById(Long id) {
	        return addressRepository.findById(id);
	    }
	    
//	    Long customerId
//	     Optional<Customer> customer = customerRepository.findById(customerId);
//        customer.ifPresent(product::setCustomer);

	    public Address saveAddress(Address address,Long userrId) {
	    	
	    	Optional <Userr> userr = userrRepository.findById(userrId);
	    	userr.ifPresent(address::setUserr);
	    	
	        return addressRepository.save(address);
	    }

	    public void deleteAddress(Long id) {
	        addressRepository.deleteById(id);
	    }

	    public Address updateAddress(Long id, Address address) {
	        Optional<Address> existingAddressOptional = addressRepository.findById(id);
	        if (existingAddressOptional.isPresent()) {
	            Address existingAddress = existingAddressOptional.get();
	            existingAddress.setState(address.getState());
	            existingAddress.setCity(address.getCity());
	            existingAddress.setAddress(address.getAddress());
	            existingAddress.setPincode(address.getPincode());
	            return addressRepository.save(existingAddress);
	        } else {
	            throw new RuntimeException("Address with ID " + id + " not found");
	        }
	    }
	    
	    public List<Address> getAddressesByUserId(Long userId) {
	        return addressRepository.findByUserrId(userId);
	    }

	   
}
