package org.afripay.afripay.transactions.service;


import org.afripay.afripay.transactions.dto.TransactionListDTO;

public interface TransactionService {
    TransactionListDTO getAllTransactions(int size, int page, String status, String rrn, String toDate,
                                          String fromDate);

    long getNumbersOfAllTransactions();

    long getNumbersOfTransactionsByStatus(String status);

}