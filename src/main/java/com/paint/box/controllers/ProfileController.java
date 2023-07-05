package com.paint.box.controllers;

import com.paint.box.models.auth.User;
import com.paint.box.models.cart.Cart;
import com.paint.box.models.profile.Profile;
import com.paint.box.repositories.CartRepository;
import com.paint.box.repositories.ProfileRepository;
import com.paint.box.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileRepository profileRepository;
    private final CartRepository cartRepository;


    @Autowired
    public ProfileController(ProfileRepository profileRepository, CartRepository cartRepository) {
        this.profileRepository = profileRepository;
        this.cartRepository = cartRepository;
    }

    @Autowired
    UserService userService;

    @GetMapping
    public @ResponseBody List<Profile> findAllProfile(){
        return profileRepository.findAll();
    }

    @GetMapping("/{id}")
    public Profile getProfileById(@PathVariable Long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/self")
    public @ResponseBody Profile getSelf() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return null;
        }

        return profileRepository.findByUser_id(currentUser.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestBody Profile newProfile) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        newProfile.setUser(currentUser);
        profileRepository.save(newProfile);

        Cart cart = new Cart(newProfile);
        cartRepository.save(cart);

        newProfile.setCart(cart);

        return new ResponseEntity<>(profileRepository.save(newProfile), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Profile updateProfile(@PathVariable Long id, @RequestBody Profile updatedProfile) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        profile.setFname(updatedProfile.getFname());
        profile.setLname(updatedProfile.getLname());

        return profileRepository.save(profile);
    }

    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable Long id) {
        profileRepository.deleteById(id);
    }
}