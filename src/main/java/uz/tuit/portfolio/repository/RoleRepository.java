package uz.tuit.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.tuit.portfolio.domain.Role;
import uz.tuit.portfolio.model.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName roleName);
}
