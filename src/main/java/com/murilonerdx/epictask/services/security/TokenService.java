package com.murilonerdx.epictask.services.security;

import com.murilonerdx.epictask.entities.Usuario;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    public String SECRET = "tJARtlPFFI4OpXkKPYI1t2SuBWJNsHVUStiDPHkrYuUvp50Txd";

    public String getToken(Authentication auth) {

        Usuario user = (Usuario) auth.getPrincipal();
        Date today = new Date();
        long duration = 60000;
        Date expirationDate = new Date(today.getTime() + duration);

        return Jwts.builder()
                .setIssuer("EpicTaskApi")
                .setSubject(user.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getById(String token){
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }
}
