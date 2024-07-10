package com.ecommerce.backend.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.backend.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Long> {

	 List<Address> findByUserrId(Long userrId);

	



}
