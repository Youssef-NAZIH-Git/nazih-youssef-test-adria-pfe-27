package nazih.youssef.accountservice;

import nazih.youssef.accountservice.domain.Account;
import nazih.youssef.accountservice.domain.AccountStatus;
import nazih.youssef.accountservice.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(AccountService accountService) {
        return args -> {

            // hna Monsieur d'utilise de UUIDs constants pour faciliter hadok les requetes

            Account account1 = new Account();
            account1.setId(UUID.fromString("5644e981-dc1e-4fdb-ad05-f6845cfe5197"));
            account1.setBalance(1000);
            account1.setStatus(AccountStatus.ACTIVE);
            account1.setCustomerReference("CUST-" + 1);
            account1.setCurrency("MAD");
            accountService.saveAccount(account1);



            Account account2 = new Account();
            account2.setId(UUID.fromString("482c4860-4c0e-4313-bcff-faf4b5d47e44"));
            account2.setBalance(2000);
            account2.setStatus(AccountStatus.ACTIVE);
            account2.setCustomerReference("CUST-" + 2);
            account2.setCurrency("MAD");
            accountService.saveAccount(account2);


            Account account3 = new Account();
            account3.setId(UUID.fromString("9aa2b3c1-d37a-46ff-b202-bdcae994e6a5"));
            account3.setBalance(3000);
            account3.setStatus(AccountStatus.ACTIVE);
            account3.setCustomerReference("CUST-" + 3);
            account3.setCurrency("MAD");
            accountService.saveAccount(account3);

        };
    }
}
