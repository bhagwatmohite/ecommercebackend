package com.ecommerce.backend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;




@Entity
public class SubCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Long subcatid;
	private String name;
	
	 @ManyToOne
	   @JoinColumn(name = "category_id")
	   @JsonBackReference
	   private Category category;
	 



	public SubCategory() {
		super();
	}

	
	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public Long getSubcatid() {
		return subcatid;
	}

	public void setSubcatid(Long subcatid) {	
		this.subcatid = subcatid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
