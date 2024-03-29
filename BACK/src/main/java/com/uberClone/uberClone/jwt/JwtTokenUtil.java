package com.uberClone.uberClone.jwt;

import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uberClone.uberClone.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil {
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(String.format("%s,%s", user.getId(), user.getEmail()))
                .setIssuer("Lolo")
                .claim("user", user)
                .claim("roles", user.getRoles().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

    }

    public Long getId(String token){
         return Long.parseLong(parseClaims(token).getSubject().split(",")[0]);
    }


    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            LOGGER.error("JWT expired", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            LOGGER.error("Signature validation failed");
        }

        return false;
    }

    private boolean isRoleValid(String token, String role){
        if (this.getRoles(token).contains(role))
                return true;
        return false;
    }
    public boolean validateAccessTokenWebSocket(String token){
        if (token == null)
                return false;
        if (!this.validateAccessToken(token) ||
               !this.isRoleValid(token, "ROLE_DRIVER")){
            return false;
        }
        return true;
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    private List<String> _parseRoles(String rolesString) {
            rolesString = rolesString.substring(1, rolesString.length()-1); // Remove brackets
            rolesString = rolesString.replaceAll("'", ""); // Remove single quotes
            List<String> roles = Arrays.asList(rolesString.split(","));
            return roles;
    }

    public List<String> getRoles(String token){
        return _parseRoles(parseClaims(token).get("roles", String.class));
    }
    public String getUsername(String token){
        return parseClaims(token).get("user", User.class).getUsername();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
