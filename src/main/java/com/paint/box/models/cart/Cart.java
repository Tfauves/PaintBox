package com.paint.box.models.cart;

import com.paint.box.models.product.Product;
import com.paint.box.models.profile.Profile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "cart")
    private Profile profile;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> cartItems;

    private boolean checkedOut;
    private LocalDateTime checkoutDateTime;

    public Cart(Profile profile) {
        this.profile = profile;
        this.cartItems = new ArrayList<>();
    }

    public void addProduct(Product product) {
        cartItems.add(product);
    }

    public void removeProduct(Product product) {
        cartItems.remove(product);
    }

    public void clearCart() {
        cartItems.clear();
    }

    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Product product : cartItems) {
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(product.getQtyOnHand()));
            total = total.add(itemTotal);
        }
        return total;
    }

    public void checkout() {
        if (checkedOut) {
            throw new IllegalStateException("The cart has already been checked out.");
        }

        // payment processing logic here
        // integrate with a payment gateway/service

        checkedOut = true;
        checkoutDateTime = LocalDateTime.now();
    }
}

