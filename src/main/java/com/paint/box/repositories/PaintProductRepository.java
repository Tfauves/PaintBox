package com.paint.box.repositories;

import com.paint.box.models.PaintProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaintProductRepository extends JpaRepository<PaintProduct, Long> {
}
