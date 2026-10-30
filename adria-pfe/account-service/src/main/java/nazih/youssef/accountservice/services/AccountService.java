package nazih.youssef.accountservice.services;

import nazih.youssef.accountservice.entities.Account;
import nazih.youssef.accountservice.repositories.AccountRepository;
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
        return accountRepository.save(account);
    }


}
