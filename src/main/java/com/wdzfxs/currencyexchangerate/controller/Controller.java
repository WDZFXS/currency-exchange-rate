package com.wdzfxs.currencyexchangerate.controller;

import com.wdzfxs.currencyexchangerate.service.MainService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
public class Controller {

    private final MainService mainService;

    public Controller(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping(value = "/rate", produces = "image/gif")
    public ResponseEntity<byte[]> rate(@RequestParam("symbol") String symbol) {
        if (symbol.length() != 3) {
            return mainService.invalidSymbol();
        } else
            return new ResponseEntity<>(mainService.rate(symbol), HttpStatus.OK);

    }
}