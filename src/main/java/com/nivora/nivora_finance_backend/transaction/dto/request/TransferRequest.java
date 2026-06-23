package com.nivora.nivora_finance_backend.transaction.dto.request;

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
@Schema(description = "Money transfer request")
public class TransferRequest {

    @Schema(
            description = "Receiver user id",
            example = "2"
    )
    private Long receiverId;

    @Schema(
            description = "Amount to transfer",
            example = "25.00"
    )
    private BigDecimal amount;
}