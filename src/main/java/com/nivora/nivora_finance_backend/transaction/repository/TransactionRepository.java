package com.nivora.nivora_finance_backend.transaction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nivora.nivora_finance_backend.transaction.entity.Transaction;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySenderIdOrReceiverId(
            Long senderId,
            Long receiverId);

    List<Transaction> findBySenderId(Long senderId);

    List<Transaction> findByReceiverId(Long receiverId);
}