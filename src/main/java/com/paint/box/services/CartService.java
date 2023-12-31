package com.paint.box.services;

import com.paint.box.models.cart.Cart;
import com.paint.box.models.product.Product;
import com.paint.box.models.profile.Profile;
import com.paint.box.repositories.CartRepository;
import com.paint.box.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final PaymentService paymentService;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository, PaymentService paymentService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.paymentService = paymentService;
    }

    public Cart createCart(Profile profile) {
        Cart cart = new Cart(profile);
        return cartRepository.save(cart);
    }

    public void addProductToCart(Long cartId, Long profileId) {
        Product product = productRepository.getReferenceById(profileId);
        Cart cart = getCartById(cartId);
        cart.addProduct(product);
        product.setInventoryQty(product.getInventoryQty() -1);
        cartRepository.save(cart);
    }


    public void increaseProductQuantity(Long cartId, Long productId) {
        Cart cart = getCartById(cartId);
        Product product = productRepository.getReferenceById(productId);
        cart.increaseProductQuantity(product);
        product.setInventoryQty(product.getInventoryQty() -1);
        cartRepository.save(cart);
    }


    public void decreaseProductQuantity(Long cartId, Long productId) {
        Cart cart = getCartById(cartId);
        Product product = productRepository.getReferenceById(productId);
        cart.decreaseProductQuantity(product);
        cartRepository.save(cart);
    }


    public void removeProductFromCart(Long cartId, Long productId) {
        Cart cart = getCartById(cartId);
        Product product = productRepository.getReferenceById(productId);
        product.setInventoryQty(product.getInventoryQty() + product.getInCartQty());
        product.setInCartQty(0);
        cart.removeProduct(product);

        cartRepository.save(cart);
    }

    public void clearCart(Long cartId) {
        Cart cart = getCartById(cartId);
        for (Product item : cart.getCartItems()) {
            int inCartQty = item.getInCartQty();
            item.setInCartQty(0);
            item.setInventoryQty(item.getInventoryQty() + inCartQty);
        }
        cart.clearCart();
        cartRepository.save(cart);
    }

    public BigDecimal calculateTotal(Long cartId) {
        Cart cart = getCartById(cartId);
        return cart.calculateTotal();
    }

    public void checkoutCart(Long cartId) {
        Cart cart = getCartById(cartId);
        cart.checkout();
        cartRepository.save(cart);

        // Perform payment processing
        // reduce product qty on hand by amt purchased if order is processed
        paymentService.processPayment(cart.calculateTotal());
    }

    private Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
