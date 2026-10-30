package nazih.youssef.accountservice.service;

import nazih.youssef.accountservice.domain.Account;
import nazih.youssef.accountservice.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(UUID id) {
        return accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
    }


    public Account saveAccount(Account account) {
        if (account.getId() == null){
            account.setId(UUID.randomUUID());
        }
        return accountRepository.save(account);
    }


}
