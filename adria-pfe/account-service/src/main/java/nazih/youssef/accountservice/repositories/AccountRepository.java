package nazih.youssef.accountservice.repositories;

import nazih.youssef.accountservice.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {}
