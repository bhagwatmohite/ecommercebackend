package com.ecommerce.backend.controller;

import java.util.List;

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

import com.ecommerce.backend.entity.PlatFormCharges;
import com.ecommerce.backend.services.PlatFormChargesService;

@RestController
@CrossOrigin("*")
public class PlatFormChargesController {
	
	@Autowired
	private PlatFormChargesService service;

	@PostMapping("/add")
	public PlatFormCharges addPlatFormCharge(@RequestBody PlatFormCharges platFormCharges) {
		return service.savePlatFormCharge(platFormCharges);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<PlatFormCharges> updatePlatFormCharge(
			@PathVariable("id") Long id,
			@RequestBody PlatFormCharges updatedPlatFormCharges) {
		PlatFormCharges updatedCharge = service.updatePlatFormCharge(id, updatedPlatFormCharges);
		if (updatedCharge != null) {
			return new ResponseEntity<>(updatedCharge, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/all")
	public List<PlatFormCharges> getAllPlatFormCharges() {
		return service.getAllPlatFormCharges();
	}

	@GetMapping("/{id}")
	public PlatFormCharges getPlatFormChargeById(@PathVariable Long id) {
		return service.getPlatFormChargeById(id);
	}

	@DeleteMapping("/{id}")
	public void deletePlatFormCharge(@PathVariable Long id) {
		service.deletePlatFormCharge(id);
	}	  
	}
