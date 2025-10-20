package uz.tuit.portfolio.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import uz.tuit.portfolio.model.RoleName;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName name;

    private String description;

    @Override
    public String getAuthority() {
        return name.toString();
    }
}
