package uz.tuit.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.tuit.portfolio.domain.CV;
import uz.tuit.portfolio.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CVRepository extends JpaRepository<CV,Long> {
    List<CV> findByUser(User user);

    Optional<CV> findByIdAndUserId(Long cvId, Long id);

    @Modifying
    @Query(nativeQuery = true, value = """
        delete from cv_hobbies ch where ch.cv_id =:id and ch.hobby =:hobby
                """)
    void removeHobby(Long id, String hobby);
}
