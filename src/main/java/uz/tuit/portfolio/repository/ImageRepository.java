package uz.tuit.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.tuit.portfolio.domain.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
}
