package com.gavrilo.cityweather.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

/**
 * JwtService class for JWT-related operations
 */
@Service
public class JwtService {

    private static final String SECRED_KEY = "CEkqY7mDdqY+NL4RDAsWcxdEfBTRE7UXUsb/d49DgPfyKZWo4tX7UBj+/8SCbeCt";
    /**
     * Extracts the user email from the JWT token
     * @param token JWT token
     * @return User email
     */
    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a claim from the JWT token using a specified ClaimsResolver
     * @param token          JWT token
     * @param claimsResolver Function to resolve the claim
     * @param <T>            Type of the claim
     * @return Resolved claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token for the provided UserDetails
     * @param userDetails UserDetails for which the token is generated
     * @return Generated JWT token
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token for the provided UserDetails with a specified expiration date
     * @param userDetails    UserDetails for which the token is generated
     * @param expirationDate Expiration date of the token
     * @return Generated JWT token
     */
    public String generateToken(UserDetails userDetails, Date expirationDate) {
        return generateToken(new HashMap<>(), userDetails, expirationDate);
    }

    /**
     * Generates a JWT token with additional claims, for the provided UserDetails and expiration date
     * @param extraClaims    Additional claims to be included in the token
     * @param userDetails    UserDetails for which the token is generated
     * @param expirationDate Expiration date of the token
     * @return Generated JWT token
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            Date expirationDate
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Generates a JWT token with additional claims, for the provided UserDetails and default expiration date
     * @param extraClaims Additional claims to be included in the token
     * @param userDetails UserDetails for which the token is generated
     * @return Generated JWT token
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Checks if the JWT token is valid for the provided UserDetails
     * @param token       JWT token
     * @param userDetails UserDetails for validation
     * @return True if the token is valid, otherwise false
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserEmail(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if the JWT token is expired
     * @param token JWT token
     * @return True if the token is expired, otherwise false
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token
     * @param token JWT token
     * @return Expiration date
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the JWT token
     * @param token JWT token
     * @return Claims from the token
     */
    public Claims extractAllClaims(String token) {
        return Jwts.
                parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Gets the signing key for JWT
     * @return Signing key
     */
    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRED_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
