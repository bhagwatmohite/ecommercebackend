package com.ecommerce.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import jakarta.persistence.Id;

@Entity
public class PlatFormCharges {

	    @Id
	    @GeneratedValue
	    private Long id;

	    private int percentage;

	    // Constructors, getters, and setters
	    public PlatFormCharges() {
	    }

	    public PlatFormCharges(int percentage) {
	        this.percentage = percentage;
	    }

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public int getPercentage() {
	        return percentage;
	    }

	    public void setPercentage(int percentage) {
	        this.percentage = percentage;
	    }
	
	
}
