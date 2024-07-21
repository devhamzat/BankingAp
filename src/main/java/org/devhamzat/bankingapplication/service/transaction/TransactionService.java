package org.devhamzat.bankingapplication.service.transaction;

import org.devhamzat.bankingapplication.dto.TransactionDto;

public interface TransactionService {
    void saveTransaction(TransactionDto transactionDto);
}
