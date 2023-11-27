package me.code.filebox.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import me.code.filebox.models.User;
import org.springframework.stereotype.Component;

import java.security.Key;

/**
 * Component responsible for handling JWT token generation, validation, and extraction.
 */
@Component
@NoArgsConstructor
public class JwtTokenProvider {

    private static final String SECRET = "keyboardcat-dsahb5332fdf521123@∞©$@|£dsad123213678";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    private static final String CLAIM_ID = "id";
    private static final String CLAIM_USERNAME = "username";

    /**
     * Generates a JWT token for the given user.
     *
     * @param user The user for whom the token is generated.
     * @return The generated JWT token.
     */
    public String generateToken(User user) {
        int id = user.getId();
        String username = user.getUsername();

        return Jwts.builder()
                .setSubject(Integer.toString(id))
                .claim(CLAIM_ID, id)
                .claim(CLAIM_USERNAME, username)
                .signWith(KEY)
                .compact();
    }

    /**
     * Validates the provided JWT token.
     *
     * @param token The JWT token to validate.
     * @return True if the token is valid, false otherwise.
     */
    public boolean validate(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * Extracts the username from the provided JWT token.
     *
     * @param token The JWT token from which to extract the username.
     * @return The username extracted from the token, or null if extraction fails.
     */
    public String getUsernameFromToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token);

            return claimsJws.getBody().get(CLAIM_USERNAME, String.class);
        } catch (JwtException e) {
            return null;
        }
    }
}