package nazih.youssef.accountservice.mcp;

import nazih.youssef.accountservice.domain.Account;
import nazih.youssef.accountservice.service.AccountService;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AccountMcpTools {
    private final AccountService accountService;

    public AccountMcpTools(AccountService accountService) {
        this.accountService = accountService;
    }

    @McpTool(description = "List bank accounts with their owner reference, status, currency and current balance")
    public List<Account> listAccounts() {
        return accountService.findAll();
    }

    @McpTool(description = "Get a bank account by its UUID, including its current balance")
    public Account getAccountById(@McpToolParam(description = "Account UUID", required = true) String id) {
        return accountService.findById(UUID.fromString(id));
    }
}
