package com.paint.box.repositories;

import com.paint.box.models.auth.ERole;
import com.paint.box.models.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

}
