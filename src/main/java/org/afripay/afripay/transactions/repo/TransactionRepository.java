package org.afripay.afripay.transactions.repo;

import org.afripay.afripay.transactions.model.Transactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transactions, Long> {
    Page<Transactions> findAllByStatus(String status,
                                       Pageable pageable);
    long countAll();
    
    long countAllByStatus(String status);
    
}