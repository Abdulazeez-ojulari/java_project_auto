package org.afripay.afripay.general.dto;

import lombok.Data;

@Data
public class Response {

    private String status;

    private String responseCode;

    private Object failureReason;

    private Object data;
}