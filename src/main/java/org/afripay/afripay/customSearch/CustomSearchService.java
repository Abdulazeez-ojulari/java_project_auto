package org.afripay.afripay.customSearch;


import org.afripay.afripay.transactions.model.Transactions;
import org.springframework.data.domain.Page;

public interface CustomSearchService {

    Page<Transactions> searchPaymentTransaction(int size, int page, String status, String rrn, String fromDate,
                                                String toDate,
                                                boolean isDownload);
}