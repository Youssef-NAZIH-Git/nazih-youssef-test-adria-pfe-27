package nazih.youssef.accountservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data @Builder @Getter @Setter
public class Account {
    @Id // Pas de generated value hna pour simplifier le debugging @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String customerReference;
    private AccountStatus status;
    private String currency;
    private double balance;
}
