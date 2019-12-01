package com.ambulance.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    private static final String JWT_CLAIMS_ROLES = "roles";

    private static final String JWT_SECRET_KEY = System.getenv("APPLICATION_JWT_SECRET_KEY");

    private static final long JWT_TOKEN_VALIDITY = Long.parseLong(System.getenv("APPLICATION_JWT_SECRET_KEY_VALIDITY"));

    public String createToken(String username, List<String> roles) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put(JWT_CLAIMS_ROLES, String.join(",", roles));

        Date now = new Date();
        Date validity = new Date(now.getTime() + JWT_TOKEN_VALIDITY);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY)
                .compact();
    }

    Authentication getAuthentication(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token);

        String userName = claims.getBody().getSubject();
        String roles = claims.getBody().get(JWT_CLAIMS_ROLES, String.class);
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);

        return new UsernamePasswordAuthenticationToken(userName, null, authorities);
    }

    boolean validateToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token);
        return !claims.getBody().getExpiration().before(new Date());
    }

}
