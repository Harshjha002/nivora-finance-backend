package com.nivora.nivora_finance_backend.transaction.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nivora.nivora_finance_backend.auth.entity.User;
import com.nivora.nivora_finance_backend.auth.repository.UserRepository;
import com.nivora.nivora_finance_backend.common.exception.InsufficientFundsException;
import com.nivora.nivora_finance_backend.common.exception.ResourceNotFoundException;
import com.nivora.nivora_finance_backend.transaction.dto.request.TransferRequest;
import com.nivora.nivora_finance_backend.transaction.dto.response.TransactionResponse;
import com.nivora.nivora_finance_backend.transaction.entity.Transaction;
import com.nivora.nivora_finance_backend.transaction.entity.TransactionDirection;
import com.nivora.nivora_finance_backend.transaction.entity.TransactionStatus;
import com.nivora.nivora_finance_backend.transaction.entity.TransactionType;
import com.nivora.nivora_finance_backend.transaction.repository.TransactionRepository;
import com.nivora.nivora_finance_backend.transaction.service.TransactionService;
import com.nivora.nivora_finance_backend.wallet.entity.Wallet;
import com.nivora.nivora_finance_backend.wallet.repository.WalletRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final WalletRepository walletRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public TransactionResponse transferMoney(TransferRequest req, String idempotencyKey) {

        User sender = getCurrentUser();

        if (req.getAmount().compareTo(BigDecimal.ONE) < 0) {
            throw new IllegalArgumentException("Minimum transfer amount is $1");
        }

        if (req.getAmount().compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException(
                    "Maximum transfer amount is $100");
        }

        User receiver = userRepository.findById(
                req.getReceiverId()).orElseThrow(() -> new ResourceNotFoundException("Receiver not found"));

        if (sender.getId().equals(receiver.getId())) {
            throw new IllegalArgumentException(
                    "Cannot transfer money to yourself");
        }

        Wallet senderWallet = walletRepository
                .findByUserId(sender.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Sender wallet not found"));

        Wallet receiverWallet = walletRepository
                .findByUserId(receiver.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Receiver wallet not found"));

        if (senderWallet.getBalance()
                .compareTo(req.getAmount()) < 0) {

            throw new InsufficientFundsException(
                    "Insufficient wallet balance");
        }

        Transaction transaction = Transaction.builder()
                .senderId(sender.getId())
                .receiverId(receiver.getId())
                .amount(req.getAmount())
                .status(TransactionStatus.PENDING)
                .type(TransactionType.TRANSFER)
                .idempotencyKey(idempotencyKey)
                .build();

        senderWallet.setBalance(
                senderWallet.getBalance()
                        .subtract(req.getAmount()));

        receiverWallet.setBalance(
                receiverWallet.getBalance()
                        .add(req.getAmount()));

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        transaction.setStatus(
                TransactionStatus.SUCCESS);

        transaction = transactionRepository.save(transaction);

        return TransactionResponse.builder()
                .transactionId(transaction.getId())
                .senderId(transaction.getSenderId())
                .receiverId(transaction.getReceiverId())
                .amount(transaction.getAmount())
                .status(transaction.getStatus())
                .type(transaction.getType())
                .createdAt(transaction.getCreatedAt())
                .message("Transfer successful")
                .build();
    }

    @Override
    public List<TransactionResponse> getMyTransactions() {

        User user = getCurrentUser();

        List<Transaction> transactions = transactionRepository.findBySenderIdOrReceiverId(
                user.getId(),
                user.getId());

        return transactions.stream()
                .map(transaction -> {

                    TransactionDirection direction = transaction.getSenderId().equals(user.getId())
                            ? TransactionDirection.DEBIT
                            : TransactionDirection.CREDIT;

                    return TransactionResponse.builder()
                            .transactionId(transaction.getId())
                            .senderId(transaction.getSenderId())
                            .receiverId(transaction.getReceiverId())
                            .amount(transaction.getAmount())
                            .status(transaction.getStatus())
                            .type(transaction.getType())
                            .direction(direction)
                            .createdAt(transaction.getCreatedAt())
                            .build();
                })
                .toList();
    }

    @Override
public TransactionResponse getTransactionById(Long transactionId) {

    User user = getCurrentUser();

    Transaction transaction = transactionRepository.findById(transactionId)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Transaction not found"));

    if (!transaction.getSenderId().equals(user.getId())
            && !transaction.getReceiverId().equals(user.getId())) {

        throw new ResourceNotFoundException(
                "Transaction not found");
    }

    TransactionDirection direction =
            transaction.getSenderId().equals(user.getId())
                    ? TransactionDirection.DEBIT
                    : TransactionDirection.CREDIT;

    return TransactionResponse.builder()
            .transactionId(transaction.getId())
            .senderId(transaction.getSenderId())
            .receiverId(transaction.getReceiverId())
            .amount(transaction.getAmount())
            .status(transaction.getStatus())
            .type(transaction.getType())
            .direction(direction)
            .createdAt(transaction.getCreatedAt())
            .build();
}

    private User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        return (User) authentication.getPrincipal();
    }

}
