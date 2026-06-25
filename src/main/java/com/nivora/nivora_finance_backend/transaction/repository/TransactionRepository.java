package com.nivora.nivora_finance_backend.transaction.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nivora.nivora_finance_backend.transaction.entity.Transaction;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySenderId(Long senderId);

    List<Transaction> findByReceiverId(Long receiverId);

    Optional<Transaction> findByIdempotencyKey(String idempotencyKey);

    List<Transaction> findBySenderIdOrReceiverId(
            Long senderId,
            Long receiverId);

    Page<Transaction> findBySenderIdOrReceiverId(
            Long senderId,
            Long receiverId,
            Pageable pageable);

    @Query("""
                SELECT t
                FROM Transaction t
                WHERE
                    (t.senderId = :userId OR t.receiverId = :userId)
                AND
                    (
                        LOWER(t.status) LIKE LOWER(CONCAT('%', :keyword, '%'))
                        OR
                        LOWER(t.type) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
            """)
    List<Transaction> searchTransactions(
            @Param("userId") Long userId,
            @Param("keyword") String keyword);
}