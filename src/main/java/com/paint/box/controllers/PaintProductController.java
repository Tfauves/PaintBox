package com.paint.box.controllers;

import com.paint.box.models.PaintProduct;
import com.paint.box.repositories.PaintProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
