package com.ecommerce.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.backend.entity.Userr;

public interface UserrRepository extends JpaRepository< Userr ,Long> {

	Optional<Userr> findByEmail(String email);

	

}
