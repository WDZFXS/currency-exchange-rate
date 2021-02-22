package com.wdzfxs.currencyexchangerate.service.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.wdzfxs.currencyexchangerate.service.GiphyResponseException.TooManyRequests;
import static com.wdzfxs.currencyexchangerate.service.GiphyResponseException.Unauthorized;

@Slf4j
@ControllerAdvice
public class GiphyExceptionHandler {

    @Value("${giphy.url}")
    private String giphyUrl;
    @Value("${giphy.app-id}")
    private String giphyAppId;

    @ExceptionHandler(Unauthorized.class)
    public ResponseEntity<Object> unauthorized() {
        log.error("Invalid app-id. Site = \"{}\", api-key = \"{}\"", giphyUrl, giphyAppId);

        return new ResponseEntity<>(new ErrorMessage().internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TooManyRequests.class)
    public ResponseEntity<Object> tooManyRequests() {
        log.error("Your API Key is making too many requests. Read about requesting a Production Key to upgrade your API Key rate limits.");

        return new ResponseEntity<>(new ErrorMessage().internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}