package com.example.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.core.config.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtService {

    private JWT jwtConfig;

    public JwtService(JWT jwtConfig) {
        this.jwtConfig = jwtConfig;
    }
    // Secret Key for signing the JWT. It should be kept private.

    // Generates a JWT token for the given userName.
    public String generateAccessToken(String userName, List<String> roles) {
        // Prepare claims for the token
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", String.join(" ",roles));
        claims.put("plan", "FREE");
        // Build JWT token with claims, subject, issued time, expiration time, and
        // signing algorithm
        int tokenValidTimeInMilis = jwtConfig.getAccessValidTime();
        return Jwts.builder()
                .claims(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenValidTimeInMilis))
                .signWith(getSignKey()).compact();
    }

    public String generateRefreshToken(String userName) {
        // Prepare claims for the token
        Map<String, Object> claims = new HashMap<>();

        // Build JWT token with claims, subject, issued time, expiration time, and
        // signing algorithm
        // Token valid for 10 minutes
        int tokenValidTimeInMilis = jwtConfig.getRefreshValidTime();
        return Jwts.builder()
                .claims(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenValidTimeInMilis))
                .signWith(getSignKey()).compact();
    }
    // Creates a signing key from the base64 encoded secret.
    // returns a Key object for signing the JWT.
    private SecretKey getSignKey() {
        // Decode the base64 encoded secret key and return a Key object
        String secret = jwtConfig.getBase64Secret();
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extracts the userName from the JWT token.
    // return -> The userName contained in the token.
    public String extractUserName(String token) {
        // Extract and return the subject claim from the token
        return extractClaim(token, Claims::getSubject);
    }

    // Extracts the expiration date from the JWT token.
    // @return The expiration date of the token.
    public Date extractExpiration(String token) {
        // Extract and return the expiration claim from the token
        return extractClaim(token, Claims::getExpiration);
    }

    // Extracts a specific claim from the JWT token.
    // claimResolver A function to extract the claim.
    // return-> The value of the specified claim.
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        // Extract the specified claim using the provided function
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    // Extracts all claims from the JWT token.
    // return-> Claims object containing all claims.
    private Claims extractAllClaims(String token) {
        // Parse and return all claims from the token
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build().parseSignedClaims(token).getPayload();
    }

    // Checks if the JWT token is expired.
    // return-> True if the token is expired, false otherwise.
    public Boolean isTokenExpired(String token) {
        // Check if the token's expiration time is before the current time
        return extractExpiration(token).before(new Date());
    }

    // Validates the JWT token against the UserDetails.
    // return-> True if the token is valid, false otherwise.

    public Boolean validateToken(HttpServletRequest request, String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        // not check when at auth api
        if (request.getRequestURL().toString().contains("auth")) {
            return userName.equals(userDetails.getUsername());
        }
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
