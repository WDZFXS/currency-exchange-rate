package com.wdzfxs.currencyexchangerate.service;

import com.wdzfxs.currencyexchangerate.dto.Currency;
import com.wdzfxs.currencyexchangerate.dto.RandomGif;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.wdzfxs.currencyexchangerate.service.MainServiceException.DifferentCurrencyRateSize;
import static com.wdzfxs.currencyexchangerate.service.MainServiceException.EmptyCurrencyRate;
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
    private MediaService mediaService;
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

    @Test
    void image_validating() throws IOException {
        Currency latest = new Currency();
        latest.setRates(Map.of("EUR", 10.0));
        Currency yesterday = new Currency();
        yesterday.setRates(Map.of("EUR", 50.0));

        when(currencyService.latestSymbolRate(anyString())).thenReturn(latest);
        when(currencyService.symbolRateByDate(anyString(), anyString())).thenReturn(yesterday);

        RandomGif randomGif = mock(RandomGif.class);
        when(randomGif.getId()).thenReturn("mock");
        when(giphyService.randomGif(anyString())).thenReturn(randomGif);

        byte[] kit = new FileInputStream("src/test/resources/kit.gif").readAllBytes();
        when(mediaService.gif(anyString())).thenReturn(kit);

        assertEquals(kit, mainService.rate("EUR"));
    }

    @Test
    void throw_emptyCurrencyRate_if_emptyRates() {
        Currency latest = new Currency();
        latest.setRates(new HashMap<>());
        Currency yesterday = new Currency();
        yesterday.setRates(new HashMap<>());

        when(currencyService.latestSymbolRate(anyString())).thenReturn(latest);
        when(currencyService.symbolRateByDate(anyString(), anyString())).thenReturn(yesterday);

        assertThrows(EmptyCurrencyRate.class,
                () -> mainService.rate("RUB"));
    }

    @Test
    void throw_emptyCurrencyRate_if_ratesIsNull() {
        Currency latest = new Currency();
        latest.setRates(Map.of("RUB", 50d));
        Currency yesterday = new Currency();

        when(currencyService.latestSymbolRate(anyString())).thenReturn(latest);
        when(currencyService.symbolRateByDate(anyString(), anyString())).thenReturn(yesterday);

        assertThrows(EmptyCurrencyRate.class,
                () -> mainService.rate("RUB"));
    }

    @Test
    void throw_differentCurrencyRateSize_if_differentRatesSize() {
        Currency latest = new Currency();
        latest.setRates(Map.of("RUB", 50d));
        Currency yesterday = new Currency();
        yesterday.setRates(Map.of("RUB", 50d, "EUR", 1d));

        when(currencyService.latestSymbolRate(anyString())).thenReturn(latest);
        when(currencyService.symbolRateByDate(anyString(), anyString())).thenReturn(yesterday);

        assertThrows(DifferentCurrencyRateSize.class,
                () -> mainService.rate("RUB"));
    }
}