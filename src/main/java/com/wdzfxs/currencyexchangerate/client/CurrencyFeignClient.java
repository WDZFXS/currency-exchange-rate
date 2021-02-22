package com.wdzfxs.currencyexchangerate.client;

import com.wdzfxs.currencyexchangerate.dto.Currency;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "currency-client", url = "${open-exchange-rates.url}")
public interface CurrencyFeignClient {

    @GetMapping("/api/currencies.json?show_alternative=true&show_inactive=true")
    ResponseEntity<Map<String, String>> allCurrencies();

    @GetMapping("/api/latest.json?app_id=${open-exchange-rates.app-id}&base=${open-exchange-rates.base-currency}&prettyprint=false&show_alternative=true")
    ResponseEntity<Currency> latestSymbolRate(@RequestParam("symbol") String symbol);

    @GetMapping("/api/historical/{date}.json?app_id=${open-exchange-rates.app-id}&base=${open-exchange-rates.base-currency}&show_alternative=true&prettyprint=false")
    ResponseEntity<Currency> symbolRateByDate(@PathVariable("date") String date, @RequestParam("symbol") String symbol);
}