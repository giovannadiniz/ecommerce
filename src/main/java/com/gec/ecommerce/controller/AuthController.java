package com.gec.ecommerce.controller;

import com.gec.ecommerce.domain.User;
import com.gec.ecommerce.dto.request.AuthRequest;
import com.gec.ecommerce.dto.request.UserRequest;
import com.gec.ecommerce.dto.response.AuthResponse;
import com.gec.ecommerce.repository.UserRepository;
import com.gec.ecommerce.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("auth")
public class AuthController {

    private final UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthRequest authRequest) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        User user = (User) auth.getPrincipal();
        var token = tokenService.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRequest userRequest) {
        if (this.userRepository.findByUsername(userRequest.username()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = this.passwordEncoder.encode(userRequest.password());
        User newUser = new User(
                userRequest.fullname(),
                userRequest.username(),
                encryptedPassword,
                userRequest.email(),
                userRequest.role(),
                userRequest.phone()
        );

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}