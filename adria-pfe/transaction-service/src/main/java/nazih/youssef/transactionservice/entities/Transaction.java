package nazih.youssef.transactionservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data @Builder @Getter @Setter
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID accountId;
    private BigDecimal amount;
    private String currency;
    private String merchantCategory;
    private String country;
    private Instant createdAt;
}
