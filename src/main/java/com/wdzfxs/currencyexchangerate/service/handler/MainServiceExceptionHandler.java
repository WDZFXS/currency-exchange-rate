package com.wdzfxs.currencyexchangerate.service.handler;

import com.wdzfxs.currencyexchangerate.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.wdzfxs.currencyexchangerate.service.MainServiceException.DifferentCurrencyRateSize;
import static com.wdzfxs.currencyexchangerate.service.MainServiceException.EmptyCurrencyRate;

@Slf4j
@ControllerAdvice
public class MainServiceExceptionHandler {

    @Autowired
    private MainService mainService;

    @ExceptionHandler(EmptyCurrencyRate.class)
    public ResponseEntity emptyCurrencyRate(EmptyCurrencyRate e) {
        log.error(e.getMessage());

        return mainService.invalidSymbol();
    }

    @ExceptionHandler(DifferentCurrencyRateSize.class)
    public ResponseEntity differentCurrencyRateSize(DifferentCurrencyRateSize e) {
        log.error(e.getMessage());

        return new ResponseEntity(new ErrorMessage().internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}