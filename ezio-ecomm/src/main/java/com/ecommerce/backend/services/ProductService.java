package com.ecommerce.backend.services;
import com.ecommerce.backend.entity.Category;
import com.ecommerce.backend.entity.Customer;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.repository.CategoryRepository;
import com.ecommerce.backend.repository.CustomerRepository;
import com.ecommerce.backend.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private CustomerRepository customerRepository;

    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

//    public List<Product> getAllProducts() {
//        return productRepository.findAll();
//    }
    
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();

        // Filter products based on customer status if customer is not null
        products = products.stream()
                .filter(product -> {
                    Customer customer = product.getCustomer();
                    return customer == null || "Active".equals(customer.getStatus());
                })
                .collect(Collectors.toList());

        return products;
    }

    public Product createProduct(Product product, Long customerId,Long categoryId, MultipartFile imageFile,List<String> sizes, List<String> colors) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = imageFile.getOriginalFilename();
            String imageUrl = saveImage(imageFile, fileName);
            product.setImageUrl(imageUrl);
        }

        if (product.getQuantity() > 0) {
            product.setStock(true);
        } else {
            product.setStock(false);
        }

        Optional<Category> category = categoryRepository.findById(categoryId);
        category.ifPresent(product::setCategory);
        
        Optional<Customer> customer = customerRepository.findById(customerId);
        customer.ifPresent(product::setCustomer);
        
//        List<String> trimmedColors = product.getColors().stream()
//                .map(String::trim)
//                .collect(Collectors.toList());
//
//// Set the trimmed list of colors back to the product object
//product.setColors(trimmedColors);

        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product updatedProduct, Long customerId, Long categoryId, MultipartFile imageFile, List<String> sizes, List<String> colors, String brandname, BigDecimal sellprice) throws IOException {
        Optional<Product> existingProductOpt = productRepository.findById(productId);

        if (existingProductOpt.isEmpty()) {
            throw new IllegalArgumentException("Product not found with id: " + productId);
        }

        Product existingProduct = existingProductOpt.get();

        // Update product details if provided in updatedProduct
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        existingProduct.setStock(updatedProduct.getQuantity() > 0);
        existingProduct.setSizes(sizes);
        existingProduct.setColors(colors);
        existingProduct.setBrandname(brandname); // Update brandname
        existingProduct.setSellprice(sellprice); // Update sellprice

        // Handle image file if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = imageFile.getOriginalFilename();
            String imageUrl = saveImage(imageFile, fileName); // Implement saveImage method to save image and get URL
            existingProduct.setImageUrl(imageUrl);
        }

        // Update category if provided
        if (categoryId != null) {
            Optional<Category> category = categoryRepository.findById(categoryId);
            category.ifPresent(existingProduct::setCategory);
        }

        // Update customer if provided
        if (customerId != null) {
            Optional<Customer> customer = customerRepository.findById(customerId);
            customer.ifPresent(existingProduct::setCustomer);
        }

        // Trim and update colors if provided
        if (colors != null && !colors.isEmpty()) {
            List<String> trimmedColors = colors.stream()
                    .map(String::trim)
                    .collect(Collectors.toList());
            existingProduct.setColors(trimmedColors);
        }

        // Save the updated product
        return productRepository.save(existingProduct);
    }


    //if the file is not present then we will ceate 
    private String saveImage(MultipartFile imageFile, String fileName) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String uniqueFileName = fileName;
        Path filePath = uploadPath.resolve(uniqueFileName);
        imageFile.transferTo(filePath);

        return uniqueFileName;
    }

    public boolean deleteProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
   
}
