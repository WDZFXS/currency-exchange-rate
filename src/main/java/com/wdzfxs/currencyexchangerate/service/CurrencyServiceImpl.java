package com.wdzfxs.currencyexchangerate.service;

import com.wdzfxs.currencyexchangerate.client.CurrencyFeignClient;
import com.wdzfxs.currencyexchangerate.dto.Currency;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyFeignClient client;

    public CurrencyServiceImpl(CurrencyFeignClient client) {
        this.client = client;
    }

    @Override
    public Map<String, String> allCurrencies() {
        return client.allCurrencies().getBody();
    }

    @Override
    public Currency latestSymbolRate(String symbol) {
        return client.latestSymbolRate(symbol).getBody();
    }

    @Override
    public Currency symbolRateByDate(String date, String symbol) {
        return client.symbolRateByDate(date, symbol).getBody();
    }
}