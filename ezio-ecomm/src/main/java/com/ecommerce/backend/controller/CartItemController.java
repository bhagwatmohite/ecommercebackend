package com.ecommerce.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ecommerce.backend.entity.CartItem;
import com.ecommerce.backend.services.CartItemService;

@Controller
@CrossOrigin("*")
public class CartItemController {

	@Autowired
	private CartItemService cartItemService;
	
	
	 @PostMapping("/addcartitem")
	    public ResponseEntity<CartItem> crateCartItem(@RequestBody CartItem cartItem) {
		 CartItem savedCartItem = cartItemService.crateCartItem(cartItem);
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedCartItem);
	    }

	  @GetMapping("/getallcart")
	    public ResponseEntity<List<CartItem>> getAllCartItems() {
	        List<CartItem> cartItems = cartItemService.getAllCartItems();
	        return ResponseEntity.ok(cartItems);
	    }

	    @GetMapping("/getcartbyid/{id}")
	    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long id) {
	        CartItem cartItem = cartItemService.getCartItemById(id);
	        if (cartItem != null) {
	            return ResponseEntity.ok(cartItem);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    

	    @PutMapping("/updatecart/{id}")
	    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long id, @RequestBody CartItem cartItem) {
	        CartItem updatedCartItem = cartItemService.updateCartItem(id, cartItem);
	        if (updatedCartItem != null) {
	            return ResponseEntity.ok(updatedCartItem);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    @DeleteMapping("/deletecart/{id}")
	    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
	        boolean deleted = cartItemService.deleteCartItem(id);
	        if (deleted) {
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } 	
	    
	    @GetMapping("/user/{user_Id}/productIds")
	    public ResponseEntity<List<Long>> getProductIdsByUserr_id(@PathVariable Long user_Id) {
	        List<Long> productIds = cartItemService.getProductIdsByUserr_id(user_Id);
	        return ResponseEntity.ok(productIds);
	    }
}
