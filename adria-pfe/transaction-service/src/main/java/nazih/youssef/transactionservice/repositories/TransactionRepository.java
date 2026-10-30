package nazih.youssef.transactionservice.repositories;

import nazih.youssef.transactionservice.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
