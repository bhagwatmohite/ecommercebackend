package com.ecommerce.backend.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;


import com.ecommerce.backend.entity.SubCategory;

import com.ecommerce.backend.services.SubCategoryServices;

@RestController
@CrossOrigin("*")
public class SubCategoryController {
	
	
	@Autowired
    private SubCategoryServices subcategoryService;

    @GetMapping("/getallsubcategory")
    public ResponseEntity<List<SubCategory>> getAllSubCategories() {
        List<SubCategory> categories = subcategoryService.getAllSubCategories();
        return ResponseEntity.ok(categories);
    }
    


    @GetMapping("/subcategory/{subcatid}")
    public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable Long subcatid) {
        Optional<SubCategory> optionalSubCategory = subcategoryService.getSubCategoryById(subcatid);
        return optionalSubCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/addsubcategory")
    public ResponseEntity<SubCategory> createSubCategory(@RequestBody SubCategory subcategory) {
        SubCategory savedSubCategory = subcategoryService.createSubCategory(subcategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubCategory);
    }

    @PutMapping("/updatesubcategory/{subcatid}")
    public ResponseEntity<SubCategory> updateSubCategory(@PathVariable Long subcatid, @RequestBody SubCategory updatedSubCategory) {
        Optional<SubCategory> optionalSubCategory = subcategoryService.updateSubCategory(subcatid, updatedSubCategory);
        return optionalSubCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deletesubcategory/{subcatid}")
    public ResponseEntity<String> deleteSubCategory(@PathVariable Long subcatid) {
        String message = subcategoryService.deleteSubCategory(subcatid);
        if (message.startsWith("SubCategory with ID")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
    
    @GetMapping("/subcategory/bycategory/{categoryId}")
    public ResponseEntity<List<SubCategory>> getSubCategoriesByCategoryId(@PathVariable Long categoryId) {
        List<SubCategory> subcategories = subcategoryService.getSubCategoriesByCategoryId(categoryId);
        return ResponseEntity.ok(subcategories);
    }

}
