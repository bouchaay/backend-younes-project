package com.salon_beaute.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;

    // 🔹 Génération du token JWT avec email, rôle et nom complet
    public String generateToken(String email, String role, String name) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role) // ✅ Ajoute le rôle
                .claim("name", name) // ✅ Ajoute le nom complet
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔹 Extraction de l'email depuis le token
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 🔹 Extraction du rôle depuis le token
    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    // 🔹 Extraction du nom complet depuis le token
    public String extractName(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("name", String.class);
    }

    // 🔹 Validation du token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("⚠️ Token expiré !");
        } catch (UnsupportedJwtException e) {
            System.out.println("⚠️ Format JWT non supporté !");
        } catch (MalformedJwtException e) {
            System.out.println("⚠️ JWT mal formé !");
        } catch (SecurityException | IllegalArgumentException e) {
            System.out.println("⚠️ Problème de signature ou valeur illégale !");
        }
        return false;
    }
}
