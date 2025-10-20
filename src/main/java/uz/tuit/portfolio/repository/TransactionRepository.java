package uz.tuit.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.tuit.portfolio.domain.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
