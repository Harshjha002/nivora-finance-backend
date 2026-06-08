package com.nivora.nivora_finance_backend.wallet.dto.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddMoneyRequest {
    private Long bankId;
    private BigDecimal amount;
}
