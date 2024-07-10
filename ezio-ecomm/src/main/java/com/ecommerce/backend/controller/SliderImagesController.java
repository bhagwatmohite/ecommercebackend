package com.ecommerce.backend.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.ecommerce.backend.entity.SliderImages;
import com.ecommerce.backend.services.SliderImagesService;

@RestController
@CrossOrigin("*")
public class SliderImagesController {
	
	@Autowired
	private SliderImagesService  sliderImagesService;
	
	
	  //Add sliderImages api
	  @PostMapping("/sliderimages")
	    public ResponseEntity<SliderImages> createSliderImages(
	            @Validated @ModelAttribute SliderImages sliderImages,    
	            @RequestParam("imageFile") MultipartFile imageFile,
	            BindingResult bindingResult) throws IOException {

	        if (bindingResult.hasErrors()) {
	            return ResponseEntity.badRequest().build();
	        }

	        SliderImages savedSliderImages = sliderImagesService.createSliderImages(sliderImages, imageFile);
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedSliderImages);
	    }
	   
	  //get all images api
	  @GetMapping("/allsliderimages")
	    public ResponseEntity<List<SliderImages>> getAllSliderImages() {
	        List<SliderImages> sliderImages = sliderImagesService.getAllSliderImages();
	        if (sliderImages.isEmpty()) {
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.ok(sliderImages);
	        }
	    }
	  
	  //get SliderImages by id
	  @GetMapping("/sliderimages/{id}")
	    public ResponseEntity<SliderImages> getSliderImagesById(@PathVariable Long id) {
	        Optional<SliderImages> sliderimages = sliderImagesService.getSliderImagesById(id);
	        return sliderimages.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }
	  
	// Update SliderImages API
	  @PutMapping("/sliderimages/{id}")
	  public ResponseEntity<SliderImages> updateSliderImages(
	          @PathVariable Long id,
	          @Validated @ModelAttribute SliderImages updatedSliderImages,
	          @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
	          BindingResult bindingResult) throws IOException {

	      if (bindingResult.hasErrors()) {
	          return ResponseEntity.badRequest().build();
	      }

	      try {
	          SliderImages updatedImage = sliderImagesService.updateSliderImages(id, updatedSliderImages, imageFile);
	          return ResponseEntity.ok(updatedImage);
	      } catch (IllegalArgumentException e) {
	          return ResponseEntity.notFound().build();
	      }
	  }
	  
	  //deleted
	  @DeleteMapping("/sliderimages/{id}")
	    public ResponseEntity<String> deleteSliderImages(@PathVariable Long id) {
	        boolean deleted = sliderImagesService.deleteSliderImagesById(id);
	        if (deleted) {
	            String successMessage = "SliderImages with ID " + id + " has been successfully deleted.";
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(successMessage);
	        } else {
	            String notFoundMessage = "SliderImages with ID " + id + " is not found in the database.";
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundMessage);
	        }
	    }
}
