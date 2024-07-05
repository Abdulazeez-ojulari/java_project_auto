package org.afripay.afripay.exceptions;


import org.afripay.afripay.general.dto.Response;
import org.afripay.afripay.general.dto.ResponseConstants;
import org.afripay.afripay.general.service.GeneralService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final GeneralService generalService;

    public ExceptionController(GeneralService generalService) {
        this.generalService = generalService;
    }

    @ExceptionHandler({GeneralException.class, RemoteServiceException.class, ChangePasswordException.class, Exception.class})
    public final ResponseEntity<?> handleException(Exception ex) {
        logger.info("Error occurred, error message is {}", ex.getMessage());

        Response response;
        if (ex instanceof ChangePasswordException) {
            response = generalService.prepareFailedResponse(ResponseConstants.ResponseCode.CHANGE_PASSWORD, ResponseConstants.ResponseMessage.CHANGE_PASSWORD, ex.getMessage());
        } else {
            response = generalService.prepareFailedResponse(ResponseConstants.ResponseCode.FAILED, ResponseConstants.ResponseMessage.FAILED, ex.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public final ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        logger.info("Error occurred during request body validation, error message is {}", ex.getMessage());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        Response response = generalService.prepareFailedResponse(ResponseConstants.ResponseCode.FAILED, ResponseConstants.ResponseMessage.FAILED, errors);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler({NotFoundException.class})
    public final ResponseEntity<?> handleNotFoundException(Exception ex) {
        logger.info("Error occurred, error message is {}", ex.getMessage());

        Response response = generalService.prepareFailedResponse(ResponseConstants.ResponseCode.FAILED, ResponseConstants.ResponseMessage.FAILED, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}