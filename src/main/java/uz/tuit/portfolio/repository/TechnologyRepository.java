package uz.tuit.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.tuit.portfolio.domain.Language;
import uz.tuit.portfolio.domain.Technology;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {

    @Query(
            nativeQuery = true,
            value = """
        SELECT * 
        FROM technology t 
        WHERE t.name ILIKE CONCAT('%', :query, '%')
           OR similarity(t.name, :query) > 0.4
        ORDER BY similarity(t.name, :query) DESC
        """
    )
    List<Technology> search(@Param("query") String query);


    Optional<Technology> findByName(String technology);

    List<Technology> findByIdIn(List<Long> ids);

    @Modifying
    @Query(nativeQuery = true, value = """
        delete from cv_technologies ct where ct.cv_id =:cvId and ct.technologies_id =:technologyId
                
                                """)
    void deleteByCvIdAndTechnologyId(Long cvId, Long technologyId);

}
