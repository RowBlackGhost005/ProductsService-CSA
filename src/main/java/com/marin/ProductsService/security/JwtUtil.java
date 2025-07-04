package com.marin.ProductsService.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    /**
     * Secret KEY used to sign the tokens.
     * It MUST remain the same throughout the deployment otherwise it will throw validation errors of prior tokens.
     */
    private final SecretKey secretKey;

    /**
     * Creates a JwtUtil object using the secret key stored in the properties of this app.
     */
    private JwtUtil(@Value("${USERSERVICE_JWT_SECRET}") String secret){
        secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }


    /**
     * Extracts the username of the user used to create the token given as parameter.
     * The username comes in the signed claim subject of the token.
     *
     * @param token Token to user for extraction.
     * @return Username of the user of this token.
     */
    public String extractUsername(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Extracts the user id of the user used to create the token given as parameter.
     * This UserID should be used to log any interactions this token bearer does with the API
     *
     * @param token Token to extract the User id.
     * @return User id
     */
    public int extractUserId(String token){
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("id" , Integer.class);
    }

    /**
     * Determines whether a given token is valid or not.
     * A token can be invalid if its expired.
     *
     * @param token Token to validate
     * @return True if the token is valid, False otherwise
     */
    public boolean validateToken(String token){
        String username = extractUsername(token);

        return !isTokenExpired(token);
    }

    /**
     * Extracts the claims from the
     * @param token Token to retrieve the claims from
     * @return Claims of the token sent
     */
    public Claims getTokenClaims(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Determines whether a token is expired or not based on the time elapsed between its creation and now
     *
     * @param token Token to verify its expiration.
     * @return True if its expired, False otherwise
     */
    private boolean isTokenExpired(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration().before(new Date());
    }

}
