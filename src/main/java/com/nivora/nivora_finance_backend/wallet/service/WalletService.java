package com.nivora.nivora_finance_backend.wallet.service;

import com.nivora.nivora_finance_backend.wallet.dto.request.AddMoneyRequest;
import com.nivora.nivora_finance_backend.wallet.dto.request.TransferRequest;
import com.nivora.nivora_finance_backend.wallet.dto.request.WithdrawRequest;
import com.nivora.nivora_finance_backend.wallet.dto.response.BalanceResponse;

public interface WalletService {

    BalanceResponse getBalance();

    void addMoney(AddMoneyRequest req);

    void withdrawMoney(WithdrawRequest req);

    void transferMoney(
            TransferRequest req,
            String idempotencyKey);
}
