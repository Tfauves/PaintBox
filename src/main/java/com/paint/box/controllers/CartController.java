package com.paint.box.controllers;

import com.paint.box.models.auth.User;
import com.paint.box.models.cart.Cart;
import com.paint.box.repositories.CartRepository;
import com.paint.box.repositories.ProductRepository;
import com.paint.box.repositories.ProfileRepository;
import com.paint.box.services.CartService;
import com.paint.box.models.product.Product;
import com.paint.box.models.profile.Profile;
import com.paint.box.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final ProfileRepository profileRepository;

    @Autowired
    public CartController(CartService cartService, CartRepository cartRepository, ProductRepository productRepository, UserService userService, ProfileRepository profileRepository) {
        this.cartService = cartService;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.profileRepository = profileRepository;
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Profile profile) {
        Cart cart = cartService.createCart(profile);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/add/{productId}")
    public ResponseEntity<Void> addProductToCart(@PathVariable Long productId) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Profile userProfile = profileRepository.findByUser_id(currentUser.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        cartService.addProductToCart(userProfile.getCart().getId(), productId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/increase/{productId}")
    public ResponseEntity<Void> increaseProductQuantity(@PathVariable Long productId) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Profile userProfile = profileRepository.findByUser_id(currentUser.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Product product = productRepository.getReferenceById(productId);

        cartService.increaseProductQuantity(userProfile.getCart().getId(), productId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/decrease/{productId}")
    public ResponseEntity<Void> decreaseProductQuantity(@PathVariable Long productId) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Profile userProfile = profileRepository.findByUser_id(currentUser.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        cartService.decreaseProductQuantity(userProfile.getCart().getId(), productId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/remove/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long productId) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Profile userProfile = profileRepository.findByUser_id(currentUser.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        cartService.removeProductFromCart(userProfile.getCart().getId(), productId);
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