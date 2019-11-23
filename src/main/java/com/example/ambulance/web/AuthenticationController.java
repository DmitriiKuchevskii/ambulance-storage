package com.example.ambulance.web;

import com.example.ambulance.domain.User;
import com.example.ambulance.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping("")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authManager;

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequest data) {
        String username = data.getUsername();
        String password = data.getPassword();
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        String token = jwtTokenProvider.createToken(username, ((User)auth.getPrincipal()).getRoles());
        return ok(Map.of("username", username, "token", token));
    }
}
