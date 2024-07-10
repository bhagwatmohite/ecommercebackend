package com.ecommerce.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.backend.entity.WishList;

@Repository
public interface WishListRepository extends JpaRepository<WishList,Long> {

	List<WishList> findByUserrId(Long userrId);

	
}
