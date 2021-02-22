package com.wdzfxs.currencyexchangerate.service;

import com.wdzfxs.currencyexchangerate.dto.Currency;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MainServiceTest {

    @Autowired
    private MainService mainService;
    @MockBean
    private CurrencyService currencyService;
    @MockBean
    private GiphyService giphyService;
    @MockBean
    private MediaService service;
    @Value("${giphy.tag.positive}")
    private String positiveTag;
    @Value("${giphy.tag.negative}")
    private String negativeTag;

    @Test
    void return_true_if_more() {
        Currency latest = new Currency();
        latest.setRates(Map.of("EUR", 50.0));
        Currency yesterday = new Currency();
        yesterday.setRates(Map.of("EUR", 10.0));

        when(currencyService.latestSymbolRate(anyString())).thenReturn(latest);
        when(currencyService.symbolRateByDate(anyString(), anyString())).thenReturn(yesterday);

        assertTrue(mainService.estimate(yesterday, latest));
    }

    @Test
    void return_false_if_less() {
        Currency latest = new Currency();
        latest.setRates(Map.of("EUR", 10.0));
        Currency yesterday = new Currency();
        yesterday.setRates(Map.of("EUR", 50.0));

        when(currencyService.latestSymbolRate(anyString())).thenReturn(latest);
        when(currencyService.symbolRateByDate(anyString(), anyString())).thenReturn(yesterday);

        assertFalse(mainService.estimate(yesterday, latest));
    }
}