package nazih.youssef.transactionservice;

import nazih.youssef.transactionservice.entities.Transaction;
import nazih.youssef.transactionservice.services.TransactionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableFeignClients
public class TransactionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(TransactionService transactionService) {
        return args -> {
            List<UUID> ids = List.of(UUID.fromString("5644e981-dc1e-4fdb-ad05-f6845cfe5197"), UUID.fromString("482c4860-4c0e-4313-bcff-faf4b5d47e44") ,UUID.fromString("9aa2b3c1-d37a-46ff-b202-bdcae994e6a5"));

            for (UUID id : ids){
                Transaction transaction = new Transaction();
                transaction.setCurrency("MAD");
                transaction.setCountry("Maroc");
                transaction.setCreatedAt(Instant.now());
                transaction.setMerchantCategory("E-Commerce");
                transaction.setAmount(BigDecimal.valueOf(Math.random() * 1000));
                transaction.setAccountId(id);
                transactionService.saveTransaction(transaction);
            }
        };
    }
}
