package nazih.youssef.transactionservice.mcp;

import nazih.youssef.transactionservice.domain.Transaction;
import nazih.youssef.transactionservice.service.TransactionService;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TransactionMcpTools {
    private final TransactionService transactionService;

    public TransactionMcpTools(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @McpTool(description = "List recorded bank transactions with their amount, account, currency, merchant category, country and date")
    public List<Transaction> listTransactions() {
        return transactionService.findAll();
    }

    @McpTool(description = "Get a recorded bank transaction by its UUID")
    public Transaction getTransactionById(@McpToolParam(description = "Transaction UUID", required = true) String id) {
        return transactionService.findById(UUID.fromString(id));
    }
}
