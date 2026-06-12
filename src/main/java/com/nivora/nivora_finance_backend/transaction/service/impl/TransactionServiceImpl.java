package com.nivora.nivora_finance_backend.transaction.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nivora.nivora_finance_backend.transaction.dto.request.TransferRequest;
import com.nivora.nivora_finance_backend.transaction.dto.response.TransactionResponse;
import com.nivora.nivora_finance_backend.transaction.repository.TransactionRepository;
import com.nivora.nivora_finance_backend.transaction.service.TransactionService;
import com.nivora.nivora_finance_backend.wallet.repository.WalletRepository;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final WalletRepository walletRepository;

    @Override
    public TransactionResponse transferMoney(TransferRequest req, String idempotencyKey) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'transferMoney'");
    }

    @Override
    public List<TransactionResponse> getMyTransactions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMyTransactions'");
    }

    @Override
    public TransactionResponse getTransactionById(Long transactionId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTransactionById'");
    }
    
}
