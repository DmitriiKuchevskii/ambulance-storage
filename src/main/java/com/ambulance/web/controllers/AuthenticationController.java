package com.ambulance.web.controllers;

import com.ambulance.domain.hibernate.entities.User;
import com.ambulance.security.jwt.JwtTokenProvider;
import com.ambulance.web.AmbulanceApi;
import com.ambulance.web.requests.AuthenticationRequest;
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
@RequestMapping(AmbulanceApi.API_LOGIN_ROOT_REQUEST_MAP)
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authManager;

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(AmbulanceApi.API_LOGIN)
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest data) {
        String username = data.getUsername();
        String password = data.getPassword();
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        String token = jwtTokenProvider.createToken(username, ((User)auth.getPrincipal()).getRoles());
        return ok(Map.of("username", username, "token", token));
    }
}
