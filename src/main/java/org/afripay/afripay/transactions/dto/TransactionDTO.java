package org.afripay.afripay.transactions.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionDTO {

    private String rrn;
    private String pan;
    private BigDecimal amount;
    private String status;
    private String processedBy;
    private String responseCode;
    private String responseDescription;
    private Date createdDate;

}