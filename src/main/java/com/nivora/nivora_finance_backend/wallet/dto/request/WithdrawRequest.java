package com.nivora.nivora_finance_backend.wallet.dto.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Withdraw money request")
public class WithdrawRequest {

    @Schema(
            description = "Amount to withdraw from wallet",
            example = "50.00"
    )
    private BigDecimal amount;
}