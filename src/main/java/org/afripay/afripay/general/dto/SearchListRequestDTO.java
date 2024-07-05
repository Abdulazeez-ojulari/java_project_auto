package org.afripay.afripay.general.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SearchListRequestDTO {

    @NotNull(message = "Size must be provided, maximum is 100")
    private int size;

    @NotNull(message = "Page must be provided, minimum is 0")
    private int page = 0;

    private String fromDate;

    private String toDate;

}