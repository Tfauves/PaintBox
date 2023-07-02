package com.paint.box.controllers;

import com.paint.box.models.cart.Cart;
import com.paint.box.services.CartService;
import com.paint.box.models.product.Product;
import com.paint.box.models.profile.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Profile profile) {
        Cart cart = cartService.createCart(profile);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/{cartId}/add")
    public ResponseEntity<Void> addProductToCart(@PathVariable Long cartId, @RequestBody Long productId) {
        cartService.addProductToCart(cartId, productId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{cartId}/remove")
    public ResponseEntity<Void> removeProductFromCart(
            @PathVariable Long cartId,
            @RequestBody Product product) {
        cartService.removeProductFromCart(cartId, product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{cartId}/total")
    public ResponseEntity<BigDecimal> calculateTotal(@PathVariable Long cartId) {
        BigDecimal total = cartService.calculateTotal(cartId);
        return ResponseEntity.ok(total);
    }

    @PostMapping("/{cartId}/checkout")
    public ResponseEntity<Void> checkoutCart(@PathVariable Long cartId) {
        cartService.checkoutCart(cartId);
        return ResponseEntity.ok().build();
    }
}