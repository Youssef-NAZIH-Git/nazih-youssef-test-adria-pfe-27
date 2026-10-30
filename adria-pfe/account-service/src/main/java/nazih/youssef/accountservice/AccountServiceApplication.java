package nazih.youssef.accountservice;

import nazih.youssef.accountservice.entities.Account;
import nazih.youssef.accountservice.enums.AccountStatus;
import nazih.youssef.accountservice.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(AccountService accountService) {
        return args -> {

            for (int i = 1; i <= 3; i++) {
                Account account = new Account();
                account.setBalance(Math.random() * 1000);
                account.setStatus(AccountStatus.ACTIVE);
                account.setCustomerReference("CUST-" + i);
                account.setCurrency("MAD");
                accountService.saveAccount(account);
            }


        };
    }
}
