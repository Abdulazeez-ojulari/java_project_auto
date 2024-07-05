package org.afripay.afripay.general.service;


import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import org.afripay.afripay.general.dto.Response;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.HashMap;

public interface GeneralService {

    String getAsString(Object o);

    //used to format the return value of the http post or get request
    String getResponseAsString(HttpResponse<JsonNode> response);

    HashMap<Integer, String> getResponseAsString(HttpResponse<JsonNode> response, boolean getStatus);

    boolean isStringInvalid(String string);

    BigDecimal getAmountAsBigDecimal(String amountString, boolean isKobo);

    Pageable getPageableObject(int size, int page);

    //used to format failure response body
    Response prepareFailedResponse(String code, String status, Object message);
    Response preparedFailedResponse(Object data);

    //used to format success response body
    Response prepareSuccessResponse(Object data);

    Response prepareSuccessResponse(String code, String status, String message);

    <T> T objectMapperReadValue(Object object, Class<T> aClass);

    String objectMapperWriteToString(Object object);

}