package uz.tuit.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.tuit.portfolio.domain.Portfolio;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    @Modifying
    @Query(nativeQuery = true, value = """
        delete from portfolio_hobbies ph where ph.portfolio_id =:id and ph.hobby =:hobby
                                                              \s""")
    void deleteHobby(Long id, String hobby);

}
