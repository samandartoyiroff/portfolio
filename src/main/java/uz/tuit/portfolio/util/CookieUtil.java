package uz.tuit.portfolio.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.security.JwtUtil;

@Component
@RequiredArgsConstructor
public class CookieUtil {

    private final JwtUtil jwtUtil;

    @Value("${json.web.token.cookie-name}")
    private String jwtCookieName;



    public ResponseCookie generateJwtCookie(User user) {
        String token = jwtUtil.generateToken(user.getUsername());
        System.out.println("token: " + token);
        return ResponseCookie.from(jwtCookieName, token)
                .path("/api/v1")
                .maxAge(24 * 60 * 60)
                .httpOnly(true)
                .build();
    }



}
