package org.afripay.afripay.transactions.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.afripay.afripay.general.dto.PageableResponseDTO;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionListDTO extends PageableResponseDTO {

    private List<TransactionDTO> transactionDTOS;
}