package nazih.youssef.transactionservice.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class Account {
    private UUID id;
    private String customerReference;
    private String status;
    private String currency;
    private double balance;
}
