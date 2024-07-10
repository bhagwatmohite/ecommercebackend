package com.ecommerce.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ecommerce.backend.entity.SubCategory;

import com.ecommerce.backend.repository.SubCategoryRepository;

@Service
public class SubCategoryServices {
	
	@Autowired
    private SubCategoryRepository subcategoryRepository;

    public List<SubCategory> getAllSubCategories() {
        return subcategoryRepository.findAll();
    }

    public Optional<SubCategory> getSubCategoryById(Long subcatid) {
        return subcategoryRepository.findById(subcatid);
    }

    public SubCategory createSubCategory(SubCategory subcategory) {
        return subcategoryRepository.save(subcategory);
    }


    public Optional<SubCategory> updateSubCategory(Long subcatid, SubCategory updatedSubCategory) {
        Optional<SubCategory> optionalSubCategory = subcategoryRepository.findById(subcatid);
        if (optionalSubCategory.isPresent()) {
            updatedSubCategory.setSubcatid(subcatid);
            return Optional.of(subcategoryRepository.save(updatedSubCategory));
        } else {
            return Optional.empty();
        }
    }

    public String deleteSubCategory(Long subcatid) {
        Optional<SubCategory> optionalSubCategory = subcategoryRepository.findById(subcatid);
        if (optionalSubCategory.isPresent()) {
            subcategoryRepository.deleteById(subcatid);
            return "SubCategory with ID " + subcatid + " has been deleted successfully.";
        } else {
            return "SubCategory with ID " + subcatid + " is not present in the database.";
        }
    }
    
    public List<SubCategory> getSubCategoriesByCategoryId(Long categoryId) {
        return subcategoryRepository.findByCategoryId(categoryId);
    }


}
