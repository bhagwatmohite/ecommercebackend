package com.ecommerce.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class WishList {

	
	@Id
	@GeneratedValue
	private Long id;
	
	private Long userrId;
	private Long productId;
	
	
	public WishList() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getUserrId() {
		return userrId;
	}


	public void setUserrId(Long userrId) {
		this.userrId = userrId;
	}


	public Long getProductId() {
		return productId;
	}


	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	
	
}
