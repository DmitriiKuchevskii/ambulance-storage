package com.example.ambulance.web;

import com.example.ambulance.domain.User;
import com.example.ambulance.repository.UserRepository;
import com.example.ambulance.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository users;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequest data) {
        String username = data.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
        String token = jwtTokenProvider.createToken(username,
                users.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"))
                        .getRoles()
        );
        return ok(Map.of("username", username, "token", token));
    }

    @PostMapping("api/admin/newUser")
    public ResponseEntity newUser(@RequestBody NewUserRequest newUser) {
        this.users.save(User.builder()
                .username(newUser.getUsername())
                .password(this.passwordEncoder.encode(newUser.getPassword()))
                .roles(newUser.getRoles())
                .build()
        );
        return ok(Map.of("username", newUser.getUsername(), "roles", newUser.getRoles()));
    }
}
