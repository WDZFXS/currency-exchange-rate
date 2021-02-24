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

    @GetMapping("${open-exchange-rates.api-path.currencies}")
    ResponseEntity<Map<String, String>> allCurrencies();

    @GetMapping("${open-exchange-rates.api-path.lates}")
    ResponseEntity<Currency> latestSymbolRate(@RequestParam("symbols") String symbol);

    @GetMapping("${open-exchange-rates.api-path.historical}")
    ResponseEntity<Currency> symbolRateByDate(@PathVariable("date") String date, @RequestParam("symbols") String symbol);
}