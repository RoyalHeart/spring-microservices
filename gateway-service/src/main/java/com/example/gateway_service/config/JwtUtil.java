package com.example.gateway_service.config;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private CustomProperties customProperties;

    public JwtUtil(CustomProperties customProperties) {
        this.customProperties = customProperties;
    }

    // Creates a signing key from the base64 encoded secret.
    // returns a Key object for signing the JWT.
    private SecretKey getSignKey() {
        // Decode the base64 encoded secret key and return a Key object
        String secret = customProperties.getJwt().getBase64Secret();
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
    public Claims extractAllClaims(String token) {
        // Parse and return all claims from the token
        Claims claims = Jwts.parser()
                .verifyWith(getSignKey())
                .build().parseSignedClaims(token).getPayload();
        return claims;
    }

    // Checks if the JWT token is expired.
    // return-> True if the token is expired, false otherwise.
    public Boolean isTokenExpired(String token) {
        // Check if the token's expiration time is before the current time
        Date tokenDate = extractExpiration(token);
        return tokenDate.before(new Date());
    }

    // Validates the JWT token against the UserDetails.
    // return-> True if the token is valid, false otherwise.
    public Boolean validateToken(String requestUrl, String token, String username) {
        if (ObjectUtils.isEmpty(username)) {
            return false;
        }
        final String userName = extractUserName(token);
        // not check when at auth api
        if (requestUrl.contains("auth")) {
            return userName.equals(username);
        }
        return (userName.equals(username) && !isTokenExpired(token));
    }

    public boolean isInvalid(String token) {
        return isTokenExpired(token);
    }
}
