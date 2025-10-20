package uz.tuit.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.tuit.portfolio.domain.UserSubscription;
import uz.tuit.portfolio.model.UserSubscriptionStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    @Query("""
    SELECT u
    FROM UserSubscription u
    WHERE u.startDate BETWEEN :fromTime AND :toTime
      AND (:subscriptionId IS NULL OR u.subscription.id = :subscriptionId)
      AND (:status IS NULL OR u.subscriptionStatus = :status)
    """)
    List<UserSubscription> filterSubscription(LocalDate fromTime,
                                              LocalDate toTime,
                                              Long subscriptionId,
                                              UserSubscriptionStatus status);

    List<UserSubscription> findByUserId(Long id);
}
