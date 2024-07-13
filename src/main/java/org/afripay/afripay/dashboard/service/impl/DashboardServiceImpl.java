package org.afripay.afripay.dashboard.service.impl;

import lombok.RequiredArgsConstructor;
import org.afripay.afripay.dashboard.service.DashboardService;
import org.afripay.afripay.transactions.service.TransactionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    
    private final TransactionService transactionService;

    @Override
    public Object getDashboardTransactions(String name, int size, int page, String status, String rrn, String toDate,
                                           String fromDate) {
        
        return transactionService.getAllTransactions(size, page, status, rrn, toDate, fromDate);
    }

    @Override
    public long getNumbersOfAllTransactions() {
        
        return transactionService.getNumbersOfAllTransactions();
    }


    @Override
    public long getDashboardNumbersOfTransactionsByStatus(String status) {
    
        return transactionService.getNumbersOfTransactionsByStatus(status);
    }
}