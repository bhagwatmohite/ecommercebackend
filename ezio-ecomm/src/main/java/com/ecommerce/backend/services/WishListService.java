package com.ecommerce.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ecommerce.backend.entity.WishList;

import com.ecommerce.backend.repository.WishListRepository;

@Service
public class WishListService {

	 @Autowired
	    private WishListRepository wishListRepository;
	    
	    public List<WishList> getAllWishList() {
	        return wishListRepository.findAll();
	    }
	    
	    public WishList createWishList(WishList wishList) {
	        return wishListRepository.save(wishList);
	    }
	    
	    public WishList getWishListById(Long id) {
	        Optional<WishList> wishList = wishListRepository.findById(id);
	        return wishList.orElse(null);
	    }
	    
	    public boolean deleteWishList(Long id) {
	        if (wishListRepository.existsById(id)) {
	            wishListRepository.deleteById(id);
	            return true;
	        }
	        return false;
	    }
	    
	    public List<Long> getProductIdsByUserrId(Long userrId) {
	        List<WishList> wishList = wishListRepository.findByUserrId(userrId);
	        return wishList.stream()
	                .map(WishList::getProductId)
	                .collect(Collectors.toList());
	    }
}
