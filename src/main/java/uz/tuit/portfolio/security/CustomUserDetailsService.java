package uz.tuit.portfolio.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            User admin = byUsername.get();
            System.out.println(admin);
            return admin;
        }
        throw new UsernameNotFoundException(username);
    }
}