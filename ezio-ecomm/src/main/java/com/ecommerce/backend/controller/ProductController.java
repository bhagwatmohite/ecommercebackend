package com.ecommerce.backend.controller;

import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.getProductById(id);
        return optionalProduct.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/allproduct")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(products);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(
            @Validated @ModelAttribute Product product,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("customerId") Long customerId,
           
         
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("sizes") List<String> sizes, // Receive list of sizes
            @RequestParam("colors") List<String> colors,
            BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Product savedProduct = productService.createProduct(product,customerId, categoryId, imageFile,sizes, colors);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @Validated @ModelAttribute Product updatedProduct,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) MultipartFile imageFile,
            @RequestParam(required = false) List<String> sizes,
            @RequestParam(required = false) List<String> colors,
            @RequestParam(required = false) String brandname,
            @RequestParam(required = false) BigDecimal sellprice) {

        try {
            Product updated = productService.updateProduct(id, updatedProduct, customerId, categoryId, imageFile, sizes, colors, brandname, sellprice);
            if (updated != null) {
                return ResponseEntity.ok(updated);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProductById(id);
        if (deleted) {
            String successMessage = "Product with ID " + id + " has been successfully deleted.";
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(successMessage);
        } else {
            String notFoundMessage = "Product with ID " + id + " is not found in the database.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundMessage);
        }
    }
}
