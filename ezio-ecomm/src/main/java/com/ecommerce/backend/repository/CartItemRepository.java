package com.ecommerce.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.backend.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	  List<CartItem> findByUserrId(Long userrId);

	

	

}
