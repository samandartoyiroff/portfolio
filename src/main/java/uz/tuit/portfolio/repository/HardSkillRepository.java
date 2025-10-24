package uz.tuit.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.tuit.portfolio.domain.HardSkill;

import java.util.List;
import java.util.Optional;

@Repository
public interface HardSkillRepository extends JpaRepository<HardSkill, Long> {

    @Query("from HardSkill h where h.name =:hardSkill")
    Optional<HardSkill> findByName(String hardSkill);

    @Query(
            nativeQuery = true,
            value = """
                SELECT *
                FROM hard_skill hs
                WHERE 
                    (:query IS NULL OR :query = '')
                    OR (
                        hs.name ILIKE CONCAT('%', :query, '%')
                        OR similarity(hs.name, :query) > 0.2
                    )
                ORDER BY 
                    CASE 
                        WHEN :query IS NULL OR :query = '' THEN hs.id
                        ELSE similarity(hs.name, :query)
                    END DESC
                LIMIT 30
                """
    )
    List<HardSkill> search(@Param("query") String query);


    List<HardSkill> findByIdIn(List<Long> hardSkillIds);

    @Modifying
    @Query(nativeQuery = true, value = """
        delete from cv_hard_skills hs where hs.hard_skills_id =:id and hs.cv_id =:cvId
                               \s""")
    void removeFromUserHardSkillTable(Long id, Long cvId);

    @Modifying
    @Query(nativeQuery = true, value = """
        delete from portfolio_hard_skills hs where hs.portfolio_id =:portfolioId and hs.cv_id =:cvId
                               \s""")
    void removeFromUserHardSkillTableInPortfolio(Long cvId, Long portfolioId);

}
