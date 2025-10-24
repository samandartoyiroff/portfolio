package uz.tuit.portfolio.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.tuit.portfolio.model.Gender;
import uz.tuit.portfolio.model.Permission;
import uz.tuit.portfolio.model.UserStatus;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends Auditable implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    private Portfolio portfolio;

    @OneToOne(cascade = CascadeType.ALL)
    private Image profilePhoto;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_permissions", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "permissions")
    private Set<Permission> permissions = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private Address address;

    private boolean isVerified = false;

    private Boolean isSubscriber;

    private String cvUrl;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

}
