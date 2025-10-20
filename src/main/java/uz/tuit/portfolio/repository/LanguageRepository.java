package uz.tuit.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.tuit.portfolio.domain.Language;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    @Query(
            nativeQuery = true,
            value = """
                SELECT *
                FROM language l
                WHERE 
                    (:query IS NULL OR :query = '')
                    OR (
                        l.name ILIKE CONCAT('%', :query, '%')
                        OR similarity(l.name, :query) > 0.4
                    )
                ORDER BY 
                    CASE 
                        WHEN :query IS NULL OR :query = '' THEN l.id
                        ELSE similarity(l.name, :query)
                    END DESC
                LIMIT 30
                """
    )
    List<Language> search(@Param("query") String query);

}
