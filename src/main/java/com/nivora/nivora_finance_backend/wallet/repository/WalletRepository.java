package com.nivora.nivora_finance_backend.wallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nivora.nivora_finance_backend.wallet.entity.Wallet;

@Repository
public interface WalletRepository
        extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByUserId(Long userId);
}
