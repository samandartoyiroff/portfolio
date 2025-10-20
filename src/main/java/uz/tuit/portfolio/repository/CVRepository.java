package uz.tuit.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.tuit.portfolio.domain.CV;

@Repository
public interface CVRepository extends JpaRepository<CV,Long> {
}
