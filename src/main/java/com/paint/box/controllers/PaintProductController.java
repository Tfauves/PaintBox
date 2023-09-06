package com.paint.box.controllers;

import com.paint.box.models.paintproduct.PaintProduct;
import com.paint.box.repositories.PaintProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// TODO: 8/30/2023 remaining crud operations.
// TODO: 8/30/2023 a paint product needs to have the ability to take a color DTO from the color db. 
@CrossOrigin
@RestController
@RequestMapping("/api/paint")
public class PaintProductController {
    @Autowired
    PaintProductRepository paintProductRepository;

    @GetMapping
    public List<PaintProduct> getAllPaintProducts() {
        return paintProductRepository.findAll();
    }

    @GetMapping("{id}")
    public @ResponseBody PaintProduct getById(@PathVariable Long id) {
        return paintProductRepository.getReferenceById(id);

    }

    @PostMapping
    public ResponseEntity<PaintProduct> createPaintProduct(@RequestBody PaintProduct newPaintProduct) {
        return new ResponseEntity<>(paintProductRepository.save(newPaintProduct), HttpStatus.CREATED);
    }
}
