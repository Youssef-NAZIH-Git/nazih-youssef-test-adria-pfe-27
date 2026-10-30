package nazih.youssef.transactionservice.controllers;


import nazih.youssef.transactionservice.entities.Transaction;
import nazih.youssef.transactionservice.services.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public List<Transaction> findAll() {
        return transactionService.findAll();
    }

    @GetMapping("/transactions/{id}")
    public Transaction findById(@PathVariable UUID id) {
        return transactionService.findById(id);
    }

    @PostMapping("/transactions")
    public Transaction saveTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }
}
