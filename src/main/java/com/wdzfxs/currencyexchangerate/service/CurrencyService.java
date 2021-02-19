package com.wdzfxs.currencyexchangerate.service;

import com.wdzfxs.currencyexchangerate.dto.Currency;

import java.util.Map;

public interface CurrencyService {

    /*
    Get all currencies
    Schema: key = 3-letter currency code, value = full name of currency
     */
    Map<String, String> allCurrencies();

    /*
    Get current rate of symbol
     */
    Currency latestSymbolRate(String symbol);

    /*
    Get symbol rate by date (up to and including 23:59:59 UTC), except for the current UTC date
    Date format = yyyy-MM-dd
     */
    Currency symbolRateByDate(String date, String symbol);
}