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

import com.ecommerce.backend.entity.Category;
import com.ecommerce.backend.services.CategoryService;

@RestController
@CrossOrigin("*")
public class CategoryController {

	 @Autowired
	    private CategoryService categoryService;

	    @GetMapping("/getallcategory")
	    public ResponseEntity<List<Category>> getAllCategories() {
	        List<Category> categories = categoryService.getAllCategories();
	        return ResponseEntity.ok(categories);
	    }

//	    @GetMapping("/category/{id}")
//	    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
//	        Optional<Category> optionalCategory = categoryService.getCategoryById(id);
//	        return optionalCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//	    }
	    
	    
	    //after apply vendor status
	    @GetMapping("/category/{id}")
	    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
	        Optional<Category> optionalCategory = categoryService.getCategoryById(id);
	        return optionalCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }

	    @PostMapping("/addcategory")
	    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
	        Category savedCategory = categoryService.createCategory(category);
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
	    }

	    @PutMapping("/updatecategory/{id}")
	    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
	        Optional<Category> optionalCategory = categoryService.updateCategory(id, updatedCategory);
	        return optionalCategory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }

	    @DeleteMapping("/deletecategory/{id}")
	    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
	        String message = categoryService.deleteCategory(id);
	        if (message.startsWith("Category with ID")) {
	            return ResponseEntity.ok(message);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	        }
	    }

}
