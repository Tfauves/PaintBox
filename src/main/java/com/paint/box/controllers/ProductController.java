package com.paint.box.controllers;

import com.paint.box.models.product.Product;
import com.paint.box.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/search/{name}")
    public List<Product> searchProductsByName(@PathVariable String name) {
        return productRepository.findByNameContaining(name);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/{proId}")
    public @ResponseBody Product updateProduct(@PathVariable Long proId, @RequestBody Product updateData) {
        Product updateProduct = productRepository.getReferenceById(proId);

        if (updateData.getName()!= null) updateProduct.setName(updateData.getName());
        if (updateData.getDepartment() !=null) updateProduct.setDepartment(updateData.getDepartment());
        if (updateData.getPrice() != null) updateProduct.setPrice(updateData.getPrice());
        if (updateData.getInventoryQty() != 0) updateProduct.setInventoryQty(updateData.getInventoryQty());

        return productRepository.save(updateProduct);
    }

    @DeleteMapping("/{proId}")
    public ResponseEntity<String> destroyProduct(@PathVariable Long proId) {
        productRepository.deleteById(proId);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}