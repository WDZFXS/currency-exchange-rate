package com.wdzfxs.currencyexchangerate.client.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.wdzfxs.currencyexchangerate.client.CurrencyResponseException.*;

@Slf4j
@ControllerAdvice
public class CurrencyExceptionHandler {

    @Value("${open-exchange-rates.url}")
    private String exchangeUrl;
    @Value("${open-exchange-rates.app-id}")
    private String exchangeAppId;


    @ExceptionHandler(InvalidAppId.class)
    public ResponseEntity<Object> invalidAppId(InvalidAppId e) {
        log.error("Invalid app-id. Site = \"{}\", api-key = \"{}\". Cause: {}", exchangeUrl, exchangeAppId, e.getMessage());

        return new ResponseEntity<>(new ErrorMessage().internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MissingAppId.class)
    public ResponseEntity<Object> missingAppId(MissingAppId e) {
        log.error("Missing app-id. Site = \"{}\", api-key = \"{}\". Cause: {}", exchangeUrl, exchangeAppId, e.getMessage());

        return new ResponseEntity<>(new ErrorMessage().internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidBase.class)
    public ResponseEntity<Object> invalidBase() {
        String message = "Client requested rates for an unsupported base currency";
        log.error(message);

        return new ResponseEntity<>(new ErrorMessage(message), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessRestricted.class)
    public ResponseEntity<Object> accessRestricted() {
        String message = "Access restricted for repeated over-use (status: 429), or other reason given in ‘description’ (403).";
        log.error(message);

        return new ResponseEntity<>(new ErrorMessage().internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotAllowed.class)
    public ResponseEntity<Object> notAllowed() {
        String message = "Client doesn’t have permission to access requested route/feature";
        log.error(message);

        return new ResponseEntity<>(new ErrorMessage().internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<Object> notFound() {
        String message = "Client requested a non-existent resource/route";
        log.error(message);

        return new ResponseEntity<>(new ErrorMessage().internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}