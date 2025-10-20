package uz.tuit.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.tuit.portfolio.domain.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
}
