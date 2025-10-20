package uz.tuit.portfolio.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.repository.UserRepository;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Value("${json.web.token.cookie-name}")
    private String jwtCookieName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        // Cookie ichidan JWT tokenni olish
        String authorizationToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(jwtCookieName)) {
                    authorizationToken = cookie.getValue();
                    break;
                }
            }
        }
        System.out.println("Token from cookie: " + authorizationToken);

        if (authorizationToken != null) {
            String token = authorizationToken.trim(); // tokenni tozalab olish
            try {
                if (jwtUtil.isValid(token)) {
                    String username = jwtUtil.getUsername(token);
                    Optional<User> optionalUser = userRepository.findByUsername(username);
                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        var auth = new UsernamePasswordAuthenticationToken(user, null, user.getRoles());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    } else {
                        System.err.println("User topilmadi: " + username);
                    }
                } else {
                    System.err.println("Token yaroqsiz: " + token);
                }
            } catch (Exception e) {
                System.err.println("JWT tokenni o‘qishda xatolik: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
