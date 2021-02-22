package com.wdzfxs.currencyexchangerate.dto;

import lombok.Data;

import java.util.Map;

@Data
public class Currency {

    private String disclaimer;
    private String license;
    private String timestamp;
    private String base;
    private Map<String, Double> rates;
}