package com.wdzfxs.currencyexchangerate.model;


import lombok.Data;

import java.util.Map;

@Data
public class InvalidSymbol {

    private String message;
    private Map<String, String> available_symbols;

    public InvalidSymbol() {
    }

    public InvalidSymbol(String message, Map<String, String> available_symbols) {
        this.message = message;
        this.available_symbols = available_symbols;
    }
}