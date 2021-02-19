package com.wdzfxs.currencyexchangerate.dto;

import lombok.Data;

@Data
public class CurrencyError {

    private String error;
    private Integer status;
    private String message;
    private String description;
}