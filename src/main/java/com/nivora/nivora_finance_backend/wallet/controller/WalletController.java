package com.nivora.nivora_finance_backend.wallet.controller;

import com.nivora.nivora_finance_backend.wallet.dto.request.AddMoneyRequest;
import com.nivora.nivora_finance_backend.wallet.dto.request.TransferRequest;
import com.nivora.nivora_finance_backend.wallet.dto.request.WithdrawRequest;
import com.nivora.nivora_finance_backend.wallet.dto.response.BalanceResponse;
import com.nivora.nivora_finance_backend.wallet.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallet")
@AllArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/balance")
    public BalanceResponse getBalance() {
        return walletService.getBalance();
    }

    @PostMapping("/add-money")
    public void addMoney(
            @RequestBody AddMoneyRequest req) {

        walletService.addMoney(req);
    }

    @PostMapping("/withdraw")
    public void withdrawMoney(
            @RequestBody WithdrawRequest req) {

        walletService.withdrawMoney(req);
    }

    @PostMapping("/transfer")
    public void transferMoney(
            @RequestBody TransferRequest req,
            @RequestHeader("X-Idempotency-Key")
            String idempotencyKey) {

        walletService.transferMoney(
                req,
                idempotencyKey);
    }
}