package uz.tuit.portfolio.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final UserDetailsService userDetailsService;
    @Value("${json.web.token.secret}")
    private String secretKey;

    public String generateToken(String username) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("start-up-tuit")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 1000L *60*60*24*7))
                .signWith(getSignKey())
                .claim("roles", userDetails.getAuthorities())
                .compact();
    }

    private Key getSignKey() {
        byte[] decode = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decode);
    }

    public boolean isValid(String token) {
        Claims claims = getClaims(token);
        return true;
    }

    public String getUsername(String token){
        Claims claims = getClaims(token);
        return claims.getSubject();

    }
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}