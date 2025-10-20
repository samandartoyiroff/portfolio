package uz.tuit.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.tuit.portfolio.domain.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
}
