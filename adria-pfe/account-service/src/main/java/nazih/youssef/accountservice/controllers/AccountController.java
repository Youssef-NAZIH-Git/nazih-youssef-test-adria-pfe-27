package nazih.youssef.accountservice.controllers;


import nazih.youssef.accountservice.entities.Account;
import nazih.youssef.accountservice.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    public List<Account> findAll() {
        return accountService.findAll();
    }

    @GetMapping("/accounts/{id}")
    public Account findById(@PathVariable UUID id) {
        return accountService.findById(id);
    }

    @PostMapping("/accounts")
    public Account saveAccount(@RequestBody Account account) {
        return accountService.saveAccount(account);
    }

}
