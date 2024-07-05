package org.afripay.afripay.general.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.afripay.afripay.exceptions.GeneralException;
import org.afripay.afripay.exceptions.RemoteServiceException;
import org.afripay.afripay.general.dto.Response;
import org.afripay.afripay.general.dto.ResponseConstants;
import org.afripay.afripay.general.service.GeneralService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneralServiceImpl implements GeneralService {

    @Value("${max-pull-size:100}")
    private int maxPullSize;
    
    private final Gson gson;
    private final ObjectMapper objectMapper;
    
    
    //Used to format object into a string
    @Override
    public String getAsString(Object o) {
        return gson.toJson(o);
    }

    //used to format the return value of the http post or get request
    @Override
    public String getResponseAsString(HttpResponse<JsonNode> response) {
        log.info("getting JSON response as a string");

        if (Objects.nonNull(response)) {
            if (Objects.nonNull(response.getBody())) {
                String body = response.getBody().toPrettyString();
                log.info(body);
                return body;
            }
        }
        throw new RemoteServiceException("No Response from Host");
    }


    @Override
    public HashMap<Integer, String> getResponseAsString(HttpResponse<JsonNode> response, boolean getStatus) {
        log.info("getting JSON response as a Map of body and status");

        HashMap<Integer, String> codeToResponse = new HashMap<>();

        if (Objects.nonNull(response)) {
            String body = response.getBody().toPrettyString();
            log.info(body);
            codeToResponse.put(response.getStatus(), body);
            return codeToResponse;
        }
        throw new RemoteServiceException("No Response from Host");
    }


    @Override
    public boolean isStringInvalid(String string) {
        return Objects.isNull(string) || string.trim().equals("");
    }


    @Override
    public BigDecimal getAmountAsBigDecimal(String amountString, boolean isKobo) {
        log.info("getting amount as decimal");

        BigDecimal amount;

        if (Objects.isNull(amountString)) {
            throw new GeneralException("Invalid Amount");
        }

        if (amountString.equals("") || amountString.equals("0")) {
            throw new GeneralException("Invalid Amount");
        } else {
            amount = new BigDecimal(amountString);
        }

        if (isKobo) {
            return amount.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
        } else {
            return amount;
        }
    }

    @Override
    public Pageable getPageableObject(int size, int page) {
        log.info("Getting pageable object, initial size => {} and page {}", size, page);

        Pageable paged;

        page = page - 1;

        if (page < 0) {
            throw new GeneralException("Page minimum is 1");
        }

        if (size <= 0) {
            throw new GeneralException("Size minimum is 1");
        }

        if (size > maxPullSize) {
            log.info("{} greater than max size of {}, defaulting to max", size, maxPullSize);

            size = maxPullSize;
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        paged = PageRequest.of(page, size, sort);

        return paged;
    }

    //used to format failed response body
    @Override
    public Response prepareFailedResponse(String code, String status, Object message) {
        Response response = new Response();
        response.setResponseCode(code);
        response.setStatus(status);
        response.setFailureReason(message);

        log.info("ResponseCode => {}, status => {} and message => {}", code, status, message);

        return response;
    }

    @Override
    public Response prepareSuccessResponse(Object data) {
        Response response = new Response();

        String code = ResponseConstants.ResponseCode.SUCCESS;
        String status = ResponseConstants.ResponseMessage.SUCCESS;

        response.setResponseCode(code);
        response.setStatus(status);
        response.setData(data);

        log.info("ResponseCode => {}, status => {} and data => {}", code, status, data);

        return response;
    }

    @Override
    public Response prepareSuccessResponse(String code, String status, String message) {
        Response response = new Response();
        response.setResponseCode(code);
        response.setStatus(status);
        response.setData(message);

        return response;
    }

    @Override
    public <T> T objectMapperReadValue(Object object, Class<T> aClass) {
        try {
            return objectMapper.readValue(object.toString(), aClass);
        } catch (JsonProcessingException e) {
            throw new GeneralException("cannot process object to json value");

        }
    }

    @Override
    public String objectMapperWriteToString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new GeneralException("cannot process object to json value");
        }
    }

    @Override
    public Response preparedFailedResponse(Object data) {
        Response response = new Response();

        String code = ResponseConstants.ResponseCode.FAILED;
        String status = ResponseConstants.ResponseMessage.FAILED;

        response.setResponseCode(code);
        response.setStatus(status);
        response.setData(data);

        log.info("ResponseCode => {}, status => {} and data => {}", code, status, data);

        return response;
    }

}