package com.ecommerce.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.entity.CartItem;

import com.ecommerce.backend.repository.CartItemRepository;


@Service
public class CartItemService {
	

    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }
	    
    public CartItem crateCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }
    
   
    public CartItem getCartItemById(Long id) {
        Optional<CartItem> cartItem = cartItemRepository.findById(id);
        return cartItem.orElse(null);
    }

   

    public CartItem updateCartItem(Long id, CartItem cartItem) {
        if (cartItemRepository.existsById(id)) {
            cartItem.setId(id);
            return cartItemRepository.save(cartItem);
        }
        return null; // or throw an exception if needed
    }

    public boolean deleteCartItem(Long id) {
        if (cartItemRepository.existsById(id)) {
            cartItemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    
//    //productid via userrid 
    public List<Long> getProductIdsByUserr_id(Long userrId) {
        List<CartItem> cartItems = cartItemRepository.findByUserrId(userrId);
       return cartItems.stream()
                .map(CartItem::getProductId)
                .collect(Collectors.toList());
    }
 
    
}
