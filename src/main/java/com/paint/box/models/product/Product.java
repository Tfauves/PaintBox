package com.paint.box.models.product;

import jakarta.persistence.*;
import java.math.BigDecimal;

// TODO: 7/5/2023 create department entity 
@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.JOINED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String department;
    private int inCartQty;
    private int inventoryQty;
    private double price;

    public Product() {
    }

    public Product(String name, String department,Integer inCartQty, Integer inventoryQty, double price) {
        this.name = name;
        this.department = department;
        this.inCartQty = inCartQty;
        this.inventoryQty = inventoryQty;
        this.price = price;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getInventoryQty() {
        return inventoryQty;
    }

    public void setInventoryQty(int inventoryQty) {
        this.inventoryQty = inventoryQty;
    }

    public BigDecimal getPrice() {
        return BigDecimal.valueOf(price);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInCartQty() {
        return inCartQty;
    }

    public void setInCartQty(int inCartQty) {
        this.inCartQty = inCartQty;
    }
}
