package nazih.youssef.transactionservice.feign;

import nazih.youssef.transactionservice.domain.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "account-service", fallback = AccountRestClientFallback.class)
public interface AccountRestClient {
    @GetMapping("/accounts/{id}")
    Account getAccountById(@PathVariable("id") UUID id);
}
