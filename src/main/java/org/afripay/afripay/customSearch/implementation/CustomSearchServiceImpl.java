package org.afripay.afripay.customSearch.implementation;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.afripay.afripay.customSearch.CustomSearchService;
import org.afripay.afripay.transactions.model.Transactions;
import org.afripay.afripay.utils.DateUtil;
import org.afripay.afripay.utils.GeneralUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class CustomSearchServiceImpl implements CustomSearchService {

    public static final String CREATED_DATE = "createdDate";


    @PersistenceContext
    private EntityManager em;


    /**
     * This method searches the transactions table based on an array list of selected predicates, if its present it checks, else it doesn't
     */
    @Override
    public Page<Transactions> searchPaymentTransaction(int size, int page, String status, String rrn, String fromDate,
                                                       String toDate, boolean isDownload) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Transactions> cq = cb.createQuery(Transactions.class);

        Root<Transactions> root = cq.from(Transactions.class);
        List<Predicate> predicates = new ArrayList<>();

        if (valid(status)) {
            if (status.equalsIgnoreCase("Failure"))
                predicates.add(cb.equal(root.get("status"), "failure"));
            else
                predicates.add(cb.equal(root.get("status"), "success"));
        }

        if (valid(rrn)) {
            predicates.add(cb.like(cb.lower(root.get("rrn")), '%' + rrn.toLowerCase(Locale.ROOT) + '%'));
        }

        verifyTransactionDate(cb, predicates, fromDate, toDate, root.get(CREATED_DATE));

        cq.where(predicates.toArray(new Predicate[]{}));
        cq.orderBy(cb.desc(root.get(CREATED_DATE)));
        TypedQuery<?> query = em.createQuery(cq);

        return (Page<Transactions>) getPage(page, size, isDownload, query);
    }

    private PageImpl<?> getPage(int page, int size, boolean isDownload, TypedQuery<?> query) {
        Pageable paged;
        int totalRows;

        if (page > 0) {
            page = page - 1;
        }

        if (isDownload) {
            paged = PageRequest.of(0, 1000000);
            totalRows = query.getResultList().size();
            return new PageImpl<>(query.getResultList(), paged, totalRows);
        }

        totalRows = query.getResultList().size();
        paged = PageRequest.of(page, size);

        query.setFirstResult(paged.getPageNumber() * paged.getPageSize());
        query.setMaxResults(paged.getPageSize());

        return new PageImpl<>(query.getResultList(), paged, totalRows);
    }


    private void verifyTransactionDate(CriteriaBuilder cb, List<Predicate> predicates, String fromDate, String toDate, Path<Date> transactionDate) {
        Date date = getDate(fromDate);
        if (valid(fromDate) && valid(toDate)) {
            Date dateBefore = DateUtil.atStartOfDay(date);
            Date dateAfter = DateUtil.atEndOfDay(getDate(toDate));
            predicates.add(cb.between(transactionDate, dateBefore, dateAfter));
        }

        if (valid(fromDate) && !valid(toDate)) {
            Date dateBefore = DateUtil.atStartOfDay(date);
            Date dateAfter = DateUtil.atEndOfDay(DateUtil.todayDate());
            predicates.add(cb.between(transactionDate, dateBefore, dateAfter));
        }
    }


    private boolean valid(Long value) {
        return !GeneralUtil.longIsNullOrZero(value);
    }

    private boolean valid(BigDecimal value) {
        return !GeneralUtil.bigDecimalIsNull(value);
    }

    private boolean valid(String value) {
        return !GeneralUtil.stringIsNullOrEmpty(value);
    }

    private Date getDate(String dateString) {
        return DateUtil.dateTimeFullFormat(dateString);
    }

}