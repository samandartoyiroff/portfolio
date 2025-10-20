package uz.tuit.portfolio.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.tuit.portfolio.domain.Experience;

import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience,Long> {

    Optional<Experience> findById(Long id);



    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = """
    
    delete from experience e where e.id =:id and e.cv_id =:cvId
    
        """)
    void deleteByIdAndCVId(Long id, Long cvId);

}
