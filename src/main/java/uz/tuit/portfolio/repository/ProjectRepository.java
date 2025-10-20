package uz.tuit.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.tuit.portfolio.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
