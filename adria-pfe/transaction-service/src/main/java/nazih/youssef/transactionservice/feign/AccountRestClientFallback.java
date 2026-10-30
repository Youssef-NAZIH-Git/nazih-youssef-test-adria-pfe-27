package nazih.youssef.transactionservice.feign;

import nazih.youssef.transactionservice.domain.Account;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountRestClientFallback implements AccountRestClient {
    @Override
    public Account getAccountById(UUID id) {
        return null;
    }
}
