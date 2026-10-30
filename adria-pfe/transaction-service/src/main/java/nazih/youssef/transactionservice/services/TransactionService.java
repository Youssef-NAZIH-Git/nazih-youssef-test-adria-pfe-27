package nazih.youssef.transactionservice.services;

import nazih.youssef.transactionservice.entities.Transaction;
import nazih.youssef.transactionservice.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Transaction findById(UUID id) {
        return transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }


    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
