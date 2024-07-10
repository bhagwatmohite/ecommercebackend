package com.ecommerce.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.backend.entity.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {

	List<SubCategory> findByCategoryId(Long categoryId);

}
