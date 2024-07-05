package org.afripay.afripay.general.dto;

public interface ResponseConstants {

    interface ResponseCode {
        String SUCCESS = "00";
        String FAILED = "96";
        String CHANGE_PASSWORD = "97";
    }

    interface ResponseMessage {
        String SUCCESS = "SUCCESS";
        String FAILED = "FAILED";
        String CHANGE_PASSWORD = "Password change is required";
    }

}