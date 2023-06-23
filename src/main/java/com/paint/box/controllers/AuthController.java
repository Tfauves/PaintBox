package com.paint.box.controllers;
import com.paint.box.models.auth.ERole;
import com.paint.box.models.auth.Role;
import com.paint.box.models.auth.User;
import com.paint.box.payload.request.LoginRequest;
import com.paint.box.payload.request.SignupRequest;
import com.paint.box.payload.response.JwtResponse;
import com.paint.box.payload.response.MessageResponse;
import com.paint.box.repositories.RoleRepository;
import com.paint.box.repositories.UserRepository;
import com.paint.box.security.JwtUtils;
import com.paint.box.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse( jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email already used plz login or reset password"));
        }

        //create new account
        User user = new User(signupRequest.getUsername(), encoder.encode(signupRequest.getPassword()));
        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_REG_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException(("error")));
                        roles.add(adminRole);
                    }
                    case "mod" -> {
                        Role modRole = roleRepository.findByName(ERole.ROLE_MOD).orElseThrow(() -> new RuntimeException(("error")));
                        roles.add(modRole);
                    }
                    case "reg" -> {
                        Role userRole = roleRepository.findByName(ERole.ROLE_REG_USER).orElseThrow(() -> new RuntimeException(("error")));
                        roles.add(userRole);
                    }
                    default -> {
                        Role pubUserRole = roleRepository.findByName(ERole.ROLE_PUB_USER).orElseThrow(() -> new RuntimeException(("error")));
                        roles.add(pubUserRole);
                    }
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return new ResponseEntity(new MessageResponse("User Reg success"), HttpStatus.CREATED);
    }

}
