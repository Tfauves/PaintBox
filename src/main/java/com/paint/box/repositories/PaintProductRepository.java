package com.paint.box.repositories;

import com.paint.box.models.paintproduct.PaintProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaintProductRepository extends JpaRepository<PaintProduct, Long> {
}
