package me.code.filebox.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import me.code.filebox.models.User;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
@NoArgsConstructor
public class JwtTokenProvider {

    private final String secret = "keyboardcat-dsahb5332fdf521123@∞©$@|£dsad123213678";
    private final Key key = Keys.hmacShaKeyFor(secret.getBytes());

    public String generateToken(User user) {
        int id = user.getId();
        String username = user.getUsername();

        return Jwts.builder()
                .setSubject(Integer.toString(id))
                .claim("id", id)
                .claim("username", username)
                .signWith(key)
                .compact();
    }
}
