package uz.tuit.portfolio.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.User;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ApplicationAuditAware implements AuditorAware<User> {

    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty(); // register yoki anonymous holatda null qaytadi
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof User user) {
            return Optional.of(user);
        }

        return Optional.empty();
    }

}