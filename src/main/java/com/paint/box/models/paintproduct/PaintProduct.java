package com.paint.box.models.paintproduct;

import com.paint.box.models.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

// TODO: 8/30/2023 grab the string from the dto and add it to color
@Entity
public class PaintProduct extends Product {
    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Finish finish;

    @Enumerated(EnumType.STRING)
    private Size size;

    private String color;

    public PaintProduct() {
    }

    public PaintProduct(String name, String department, Integer inCartQty, Integer inventoryQty, BigDecimal price,
                        Type type, Finish finish, String color, Size size) {
        super(name, department, inCartQty, inventoryQty, price);
        this.type = type;
        this.finish = finish;
        this.color = color;
        this.size = size;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Finish getFinish() {
        return finish;
    }

    public void setFinish(Finish finish) {
        this.finish = finish;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
