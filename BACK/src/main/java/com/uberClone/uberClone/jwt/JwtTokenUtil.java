package com.uberClone.uberClone.jwt;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.uberClone.uberClone.entities.Role;
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
        System.out.println("roles :" + this.getRoles(token));
        if (this.getRoles(token).contains(role))
                return true;
        return false;
    }
    public boolean validateAccessTokenWebSocket(String token){
        System.out.println("C'est quoi cette connerie encore");
        if (!this.validateAccessToken(token) ||
               !this.isRoleValid(token, "ROLE_DRIVER")){
            System.out.println("Token was Wrong for: " + this.validateAccessToken(token) +this.isRoleValid(token, "ROLE_DRIVER") );
            return false;
        }
        return true;
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    public String getRoles(String token){
        return parseClaims(token).get("roles",String.class);
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
