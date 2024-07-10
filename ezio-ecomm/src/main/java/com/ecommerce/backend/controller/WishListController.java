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

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.ecommerce.backend.entity.WishList;

import com.ecommerce.backend.services.WishListService;

@RestController
@CrossOrigin("*")
public class WishListController {

	
	
	@Autowired
    private WishListService wishListService;

    @PostMapping("/addwishlist")
    public ResponseEntity<WishList> createWishList(@RequestBody WishList wishList) {
        WishList savedWishList = wishListService.createWishList(wishList);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWishList);
    }

    @GetMapping("/allwishlist")
    public ResponseEntity<List<WishList>> getAllWishList() {
        List<WishList> wishList = wishListService.getAllWishList();
        return ResponseEntity.ok(wishList);
    }

    @GetMapping("/wishlist/{id}")
    public ResponseEntity<WishList> getWishListById(@PathVariable Long id) {
        WishList wishList = wishListService.getWishListById(id);
        if (wishList != null) {
            return ResponseEntity.ok(wishList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/wishlist/{id}")
    public ResponseEntity<Void> deleteWishList(@PathVariable Long id) {
        boolean deleted = wishListService.deleteWishList(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/wishlist/user/{userrId}/productIds")
    public ResponseEntity<List<Long>> getProductIdsByUserrId(@PathVariable Long userrId) {
        List<Long> productIds = wishListService.getProductIdsByUserrId(userrId);
        return ResponseEntity.ok(productIds);
    }
	
}
