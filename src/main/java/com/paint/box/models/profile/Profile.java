package com.paint.box.models.profile;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.paint.box.models.auth.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paint.box.models.cart.Cart;
import jakarta.persistence.*;


@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fname;
    private String lname;


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIncludeProperties({"id", "cartItems", "cartTotal"})
    private Cart cart;

    public Profile() {
    }

    public Profile(User user, String fname, String lname) {
        this.user = user;
        this.fname = fname;
        this.lname = lname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}