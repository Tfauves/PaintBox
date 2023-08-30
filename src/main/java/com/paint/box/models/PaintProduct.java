package com.paint.box.models;

import com.paint.box.models.product.Product;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class PaintProduct extends Product {
    private String type;
    private String finish;
    private String size;
    private String color;

    public PaintProduct() {
    }

    public PaintProduct(String name, String department, Integer inCartQty, Integer inventoryQty, BigDecimal price,
                        String type, String finish, String color, String size) {
        super(name, department, inCartQty, inventoryQty, price);
        this.type = type;
        this.finish = finish;
        this.color = color;
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
