package uz.tuit.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.tuit.portfolio.domain.SoftSkill;

import java.util.List;


@Repository
public interface SoftSkillRepository extends JpaRepository<SoftSkill,Long> {


    @Query(
            nativeQuery = true,
            value = """
                SELECT *
                FROM soft_skill s
                WHERE 
                    (:query IS NULL OR :query = '')
                    OR (
                        s.name ILIKE CONCAT('%', :query, '%')
                        OR similarity(s.name, :query) > 0.4
                    )
                ORDER BY 
                    CASE 
                        WHEN :query IS NULL OR :query = '' THEN s.id
                        ELSE similarity(s.name, :query)
                    END DESC
                """
    )
    List<SoftSkill> search(@Param("query") String query);


    List<SoftSkill> findByIdIn(List<Long> softSkillIds);

    @Modifying
    @Query(nativeQuery = true, value = """
     delete from portfolio_soft_skills ss where ss.cv_id =:cvId and ss.soft_skills_id =:softSkillId 
               """)
    void deleteBySoftSkillIdAndCvId(Long softSkillId, Long cvId);

    @Modifying
    @Query(nativeQuery = true, value = """
     delete from portfolio_soft_skills ss where ss.portfolio_id =:portfolioId and ss.soft_skills_id =:softSkillId 
               """)
    void deleteBySoftSkillIdAndPortfolioId(Long softSkillId, Long portfolioId);

}
