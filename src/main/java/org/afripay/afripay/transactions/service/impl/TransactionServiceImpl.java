package org.afripay.afripay.transactions.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.afripay.afripay.customSearch.CustomSearchService;
import org.afripay.afripay.transactions.dto.TransactionDTO;
import org.afripay.afripay.transactions.dto.TransactionListDTO;
import org.afripay.afripay.transactions.model.Transactions;
import org.afripay.afripay.transactions.repo.TransactionRepository;
import org.afripay.afripay.transactions.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final CustomSearchService customSearchService;
    private final TransactionRepository transactionRepository;
    @PersistenceContext
    private EntityManager em;


    @Override
    public TransactionListDTO getAllTransactions(int size, int page, String status, String rrn, String toDate,
                                                 String fromDate) {
        log.info("Getting all transactions");

        Page<Transactions> response = customSearchService.searchPaymentTransaction(size, page, status, rrn, fromDate,
                toDate, false);

        return getTransactionListDTO(response);
    }

    @Override
    public long getNumbersOfAllTransactions() {
        
        return transactionRepository.countAll();
    }

    @Override
    public long getNumbersOfTransactionsByStatus(String status) {
        
        return transactionRepository.countAllByStatus(status);
    }


    private TransactionListDTO getTransactionListDTO(Page<Transactions> transactionPage) {
        log.info("Converting transactions page to transactions list dto");

        TransactionListDTO listDTO = new TransactionListDTO();

        List<Transactions> transactionsList = transactionPage.getContent();
        if (!transactionsList.isEmpty()) {
            listDTO.setHasNextRecord(transactionPage.hasNext());
            listDTO.setTotalCount((int) transactionPage.getTotalElements());
        }

        List<TransactionDTO> transactionDTOS = transactionsList.stream()
                .map(Transactions::getTransactionDTO).toList();

        listDTO.setTransactionDTOS(transactionDTOS);

        return listDTO;
    }

}