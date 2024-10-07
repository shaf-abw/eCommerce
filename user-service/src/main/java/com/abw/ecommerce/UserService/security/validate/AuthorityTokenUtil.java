package com.abw.ecommerce.UserService.security.validate;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

public class AuthorityTokenUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public List<String> checkPermission(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.get("authorities", List.class);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
