package org.devhamzat.bankingapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.devhamzat.bankingapplication.utils.enums.TransactionTypes;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private TransactionTypes transactionType;
    private BigDecimal amount;
    private String accountNumber;
    private String status;
}
