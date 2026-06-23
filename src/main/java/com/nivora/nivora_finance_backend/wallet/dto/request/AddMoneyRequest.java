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
@Schema(description = "Add money request")
public class AddMoneyRequest {

    @Schema(
            description = "Amount to credit into wallet",
            example = "100.00"
    )
    private BigDecimal amount;
}