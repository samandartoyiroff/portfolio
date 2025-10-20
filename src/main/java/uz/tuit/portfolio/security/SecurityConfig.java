package uz.tuit.portfolio.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security, JwtFilter jwtFilter) throws Exception {

        security.authorizeHttpRequests(authorization ->

                        // AUTH
                        authorization.requestMatchers(
                               "/api/v1/auth/admin/login",
                               "/api/v1/auth/user/login",
                               "/api/v1/auth/send-code",
                               "/api/v1/auth/verify",
                               "/api/v1/auth/register"
                                ).permitAll()

                        // IMAGE
                        .requestMatchers(
                                "/api/v1/image/upload"
                        ).permitAll()
                        // CV
                        .requestMatchers(
                                "/api/v1/cv/generate-cv"
                        ).permitAll()



                        // IMAGE
                        .requestMatchers(
                                "/api/v1/file/upload",
                                "/api/v1/file/download/**"
                        ).permitAll()



                        //SWAGGER
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        .anyRequest().authenticated()

        );

        security.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        security.cors(cors->{});
        security.csrf(AbstractHttpConfigurer::disable);

        return security.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
