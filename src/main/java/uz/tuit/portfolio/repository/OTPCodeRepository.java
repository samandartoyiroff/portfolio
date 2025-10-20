package uz.tuit.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.tuit.portfolio.domain.OTPCode;

@Repository
public interface OTPCodeRepository extends JpaRepository<OTPCode, Long> {
}
