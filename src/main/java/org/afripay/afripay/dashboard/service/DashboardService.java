package org.afripay.afripay.dashboard.service;

public interface DashboardService {
    
    Object getDashboardTransactions(String name, int size, int page, String status, String rrn, String toDate, String fromDate);

    long getNumbersOfAllTransactions();
    
    long getDashboardNumbersOfTransactionsByStatus(String status);

    
}