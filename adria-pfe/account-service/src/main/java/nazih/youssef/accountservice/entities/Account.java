package nazih.youssef.accountservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import nazih.youssef.accountservice.enums.AccountStatus;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data @Builder @Getter @Setter
public class Account {
    @Id @GeneratedValue(strategy= GenerationType.UUID)
    private String id;
    private String customerReference;
    private AccountStatus status;
    private String currency;
    private double balance;
}
