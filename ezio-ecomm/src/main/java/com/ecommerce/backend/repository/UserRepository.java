package com.ecommerce.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.backend.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {

	User findByEmail(String email);

	
}
