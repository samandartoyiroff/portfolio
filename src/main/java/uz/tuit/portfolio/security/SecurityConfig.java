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
                                "/api/v1/cv/generate-cv",
                                "/api/v1/cv/create",
                                "/api/v1/cv/update/**",
                                "/api/v1/cv/experience/add/**",
                                "/api/v1/cv/experience/update/**",
                                "/api/v1/cv/experience/delete/**",
                                "/api/v1/cv/hard-skill/add/**",
                                "/api/v1/cv/hard-skill/remove/**",
                                "/api/v1/cv/soft-skill/add/**",
                                "/api/v1/cv/soft-skill/remove/**",
                                "/api/v1/cv/education/add/**",
                                "/api/v1/cv/education/update/**",
                                "/api/v1/cv/education/remove/**",
                                "/api/v1/cv/technology/add/**",
                                "/api/v1/cv/technology/remove/**",
                                "/api/v1/cv/certificate/add/**",
                                "/api/v1/cv/certificate/update/**",
                                "/api/v1/cv/certificate/remove/**",
                                "/api/v1/cv/language-skill/add/**",
                                "/api/v1/cv/language-skill/update/**",
                                "/api/v1/cv/language-skill/remove/**",
                                "/api/v1/cv/project/add/**",
                                "/api/v1/cv/project/update/**",
                                "/api/v1/cv/project/delete/**",
                                "/api/v1/cv/hobby/add/**",
                                "/api/v1/cv/hobby/remove/**",
                                "/api/v1/cv/findById/**",
                                "/uploads/**"

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
