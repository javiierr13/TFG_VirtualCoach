package com.simulador.simuladorTactico.web.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtils {
    @Autowired
    private JwtConfig jwtConfig;

    public String generateToken(UserDetails userDetails) {
        return JWT.create()
            .withSubject(userDetails.getUsername())
            .withIssuer(jwtConfig.getIssuer())
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + jwtConfig.getAccessTokenExpires()))
            .sign(Algorithm.HMAC256(jwtConfig.getSecret()));
    }

    public String extractUsername(String token) {
        return JWT.require(Algorithm.HMAC256(jwtConfig.getSecret()))
            .build()
            .verify(token)
            .getSubject();
    }
}