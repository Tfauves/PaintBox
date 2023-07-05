package com.paint.box.repositories;

import com.paint.box.models.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c WHERE c.profile.id = :profileId")
    Optional<Cart> findByProfileId(@Param("profileId") Long profileId);

    Optional<Cart> findById(Long id);


}