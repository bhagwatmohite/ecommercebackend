package com.ecommerce.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.entity.Category;
import com.ecommerce.backend.entity.PlatFormCharges;
import com.ecommerce.backend.repository.PlatFormChargesRepository;

@Service
public class PlatFormChargesService {
	
	  @Autowired
	    private PlatFormChargesRepository repository;

	    public PlatFormCharges savePlatFormCharge(PlatFormCharges platFormCharges) {
	        return repository.save(platFormCharges);
	    }
	    
	    public PlatFormCharges updatePlatFormCharge(Long id, PlatFormCharges updatedPlatFormCharges) {
			Optional<PlatFormCharges> optional = repository.findById(id);
			if (optional.isPresent()) {
				PlatFormCharges existingPlatFormCharges = optional.get();
				existingPlatFormCharges.setPercentage(updatedPlatFormCharges.getPercentage());
				// Set other properties that you want to update

				return repository.save(existingPlatFormCharges);
			} else {
				return null; // Handle entity not found
			}
		}

	    public List<PlatFormCharges> getAllPlatFormCharges() {
	        return repository.findAll();
	    }

	    public PlatFormCharges getPlatFormChargeById(Long id) {
	        return repository.findById(id).orElse(null);
	    }

	    public void deletePlatFormCharge(Long id) {
	        repository.deleteById(id);
	    }
	   
}
