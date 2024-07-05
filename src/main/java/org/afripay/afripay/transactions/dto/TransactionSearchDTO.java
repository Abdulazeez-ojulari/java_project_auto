package org.afripay.afripay.transactions.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.afripay.afripay.general.dto.PageableRequestDTO;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionSearchDTO extends PageableRequestDTO {
    private String rrn;
    private String status;

    private String toDate;
    private String fromDate;

}