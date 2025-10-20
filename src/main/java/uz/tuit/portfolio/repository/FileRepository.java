package uz.tuit.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.tuit.portfolio.domain.File;

@Repository
public interface FileRepository extends JpaRepository<File,Long> {
}
