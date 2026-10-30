package nazih.youssef.transactionservice.services;

import nazih.youssef.transactionservice.entities.Transaction;
import nazih.youssef.transactionservice.feign.AccountRestClient;
import nazih.youssef.transactionservice.model.Account;
import nazih.youssef.transactionservice.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRestClient accountRestClient;
    private final BigDecimal maxAmount;

    public TransactionService(TransactionRepository transactionRepository, AccountRestClient accountRestClient,
                              @Value("${bank.transaction.max-amount}") BigDecimal maxAmount) {
        this.transactionRepository = transactionRepository;
        this.accountRestClient = accountRestClient;
        this.maxAmount = maxAmount;
    }


    public List<Transaction> findAll() {
        List<Transaction> transactions = transactionRepository.findAll();
        transactions.forEach(transaction -> transaction.setAccount(
                accountRestClient.getAccountById(transaction.getAccountId())));
        return transactions;
    }

    public Transaction findById(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        transaction.setAccount(accountRestClient.getAccountById(transaction.getAccountId()));
        return transaction;
    }


    public Transaction saveTransaction(Transaction transaction) {
        if (transaction.getAmount() == null || transaction.getAmount().signum() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Transaction amount must be strictly positive");
        }
        if (transaction.getAmount().compareTo(maxAmount) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Transaction amount exceeds the configured limit of " + maxAmount);
        }

        Account account = accountRestClient.getAccountById(transaction.getAccountId());

        if (account == null) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Account service is unavailable. Please try again later.");
        }
        if (!"ACTIVE".equals(account.getStatus())) {
            throw new RuntimeException("Account is not active");
        }
        if (account.getCurrency() == null || !account.getCurrency().equals(transaction.getCurrency())) {
            throw new RuntimeException("Transaction currency must match account currency");
        }

        return transactionRepository.save(transaction);
    }
}
