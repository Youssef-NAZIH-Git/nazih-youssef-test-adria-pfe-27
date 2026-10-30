package nazih.youssef.transactionservice.services;

import nazih.youssef.transactionservice.entities.Transaction;
import nazih.youssef.transactionservice.feign.AccountRestClient;
import nazih.youssef.transactionservice.model.Account;
import nazih.youssef.transactionservice.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRestClient accountRestClient;

    public TransactionService(TransactionRepository transactionRepository, AccountRestClient accountRestClient) {
        this.transactionRepository = transactionRepository;
        this.accountRestClient = accountRestClient;
    }


    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Transaction findById(UUID id) {
        return transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }


    public Transaction saveTransaction(Transaction transaction) {
        Account account = accountRestClient.getAccountById(transaction.getAccountId());

        if (!"ACTIVE".equals(account.getStatus())) {
            throw new RuntimeException("Account is not active");
        }
        if (account.getCurrency() == null || !account.getCurrency().equals(transaction.getCurrency())) {
            throw new RuntimeException("Transaction currency must match account currency");
        }

        return transactionRepository.save(transaction);
    }
}
