package com.nivora.nivora_finance_backend.transaction.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.nivora.nivora_finance_backend.transaction.dto.request.TransferRequest;
import com.nivora.nivora_finance_backend.transaction.dto.response.TransactionResponse;
import com.nivora.nivora_finance_backend.transaction.service.TransactionService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/transactions")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public TransactionResponse transferMoney(
            @RequestBody TransferRequest req,
            @RequestHeader("Idempotency-Key") String idempotencyKey) {

        return transactionService.transferMoney(
                req,
                idempotencyKey);
    }

    @GetMapping
    public List<TransactionResponse> getMyTransactions() {

        return transactionService.getMyTransactions();
    }

    @GetMapping("/{transactionId}")
    public TransactionResponse getTransactionById(
            @PathVariable Long transactionId) {

        return transactionService.getTransactionById(
                transactionId);
    }

    @GetMapping("/search")
    public List<TransactionResponse> searchTransactions(
            @RequestParam String q) {

        return transactionService.searchTransactions(q);
    }
}
