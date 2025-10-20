package uz.tuit.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import uz.tuit.portfolio.domain.Occupation;

import java.util.Optional;

@Repository
public interface OccupationRepository extends JpaRepository<Occupation, Long> {

    @Modifying
    @Query(value = "INSERT INTO occupation (name, created_at) VALUES (:name, now()) ON CONFLICT DO NOTHING", nativeQuery = true)
    void insertIfNotExists(@Param("name") String name);

    Optional<Occupation> findByName(String occupation);

}
